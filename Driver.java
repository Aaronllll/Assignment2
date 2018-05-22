package miniNet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.IIOException;

public class Driver {
	
//	the maxium number of users of this application
	private static ArrayList<User> userList = new ArrayList<>(500);
	private static ArrayList<Connection> connection = new ArrayList<>();
	
	Scanner sc = new Scanner(System.in);

	
public static ArrayList<User> getUserList() {
		return userList;
	}

	public static void setUserList(ArrayList<User> userList) {
		Driver.userList = userList;
	}

	public static ArrayList<Connection> getConnection() {
		return connection;
	}

	public static void setConnection(ArrayList<Connection> connection) {
		Driver.connection = connection;
	}

	//	add a new user
	public void addUser(String name, String image, String status, char gender, int age, String state) {
		if(Driver.userList.size() <= 500){
			if(age <= 2){
				int countParent = 0;
				Driver.userList.add(new YoungChild(name, image, status, gender, age, state));
				int index = Driver.userList.size()-1;
				System.out.println("Add a new young child successfully");
				for(int i = 0; i < Driver.connection.size(); i++){
					if(Driver.connection.get(i).toString().contains(name) && Driver.connection.get(i).getRelation() == "parent"){
						countParent++;
					}
				}
				
				if(countParent < 2){
					throw new NoParentException("The number of parents is not engouh");
				}
				boolean add = false;
				int parentNumber;
				do{
					System.out.println("Here is the user list, you can enter number to add the parent");
					this.listAllUsers();
					parentNumber = sc.nextInt();
					if(this.checkIfExist(index, parentNumber)){
						this.addParent(index, parentNumber);
						add = true;
					}else{
						System.out.println("Please try again");
					}
				}while(add && countParent == 2);
				
			}else if(age > 2 && age <= 16){
				int countParent = 0;
				int index = Driver.userList.size()-1;
				Driver.userList.add(new Child(name, image, status, gender, age, state));
				System.out.println("Add a new child successfully");
				for(int i = 0; i < Driver.connection.size(); i++){
					if(Driver.connection.get(i).getRelationPerson1().getName() == name && Driver.connection.get(i).getRelation() == "parent"){
						countParent++;
					}
				}
				
				if(countParent < 2){
					throw new NoParentException("The number of parents is not engouh");
				}
				boolean add = false;
				int parentNumber;
				do{
					System.out.println("Here is the user list, you can enter number to add the parent");
					this.listAllUsers();
					parentNumber = sc.nextInt();
					if(this.checkIfExist(index, parentNumber)){
						this.addParent(index, parentNumber);
						add = true;
					}else{
						System.out.println("Please try again");
					}
				}while(add && countParent == 2);
			}else if(age >= 17){
				Driver.userList.add(new User(name, image, status, gender, age, state));
				System.out.println("Add an adult successfully");
			}
		}else{
			System.out.println("Sorry, the user list is full, you can't add any user");
		}
		
		try{
			String gen = String.valueOf(gender);
			BufferedWriter bw = new BufferedWriter(new FileWriter("People.txt"));
			bw.write(name);
			bw.write(",");
			bw.write(image);
			bw.write(",");
			bw.write(status);
			bw.write(",");
			bw.write(gen);
			bw.write(",");
			bw.write(state);
			bw.newLine();
			bw.flush();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
//	search a user by name and show his profile and return his id
	public int search(String name){
		int index = -1;
		for(int i = 0; i < Driver.userList.size(); i++){
			if(Driver.userList.get(i).getName().equals(name)){
				System.out.println("The user is found");
				System.out.println(Driver.userList.get(i).toString());
				index = i;
				break;
			}else{
				System.out.println("Sorry, we couldn't find this user, please try again");
			}
		}
		return index;
	}
	
//	update the info
	public void updateInformation(int index){
		System.out.println("Please enter your age: ");
		int age = sc.nextInt();
		System.out.println("Please enter your state: ");
		String state = sc.nextLine();
		System.out.println("Please enter your status, if you dont want to, please just click enter ");
		String status = sc.nextLine();
		Driver.userList.get(index).setAge(age);
		Driver.userList.get(index).setState(state);
		Driver.userList.get(index).setStatus(status);
		System.out.println("The updated information is shown below");
		this.displayProfile(index);
	}
	
//	display a user's profile
	public void displayProfile(int index){
		System.out.println(Driver.userList.get(index).toString());
		
	}
	
//	delete a user
	public void deleteUser(int index){
		boolean delete = true;
		if(Driver.userList.get(index)instanceof Adult){
			for(int i = 0; i < Driver.connection.size(); i++){
				if(Driver.connection.get(i).toString().contains(Driver.userList.get(index).getName())
						&& (Driver.connection.get(i).getRelationPerson1().getAge() < 17 ||Driver.connection.get(i).getRelationPerson2().getAge() < 17)
						&& Driver.connection.get(i).getRelation() == "parent"){
					delete = false;
					throw new NoParentException("You need to delet the child of this user first");
				}
			}
			if(delete){
				Driver.userList.remove(index);
				System.out.println("Remove this user successfully");
			}
		}else{
			Driver.userList.remove(index);
			System.out.println("Remove this user successfully");
		}
	}
	
//	list all the users
	public void listAllUsers(){
		for(int i = 0; i < Driver.userList.size(); i++){
			System.out.println(i + " " + Driver.userList.get(i).getName());
		}
	}
	
//	check whether person exist in the connection
	public boolean checkIfExist(int index, int checkIndex){
		boolean personExist = false;
		for(int i = 0; i < Driver.connection.size(); i++){
			if(Driver.connection.get(i).toString().contains(Driver.userList.get(index).getName())
					&& Driver.connection.get(i).toString().contains(Driver.userList.get(checkIndex).getName())){
				personExist = true;
				break;
			}
		}
		
		if(!personExist && (index != checkIndex)){
			System.out.println("The user you select is not in the connection");
			return true;
		}else{
			System.out.println("Sorry, the user you select is already in the connection");
			return false;
		}
	}
	
//	add friend
	public void addFriend(int index, int friendIndex){
		if(Driver.userList.get(index)instanceof Adult && Driver.userList.get(friendIndex)instanceof Adult
				&& index != friendIndex){
			Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(friendIndex),"friends"));
		}else if(Driver.userList.get(index)instanceof Child && Driver.userList.get(friendIndex)instanceof Child
				&& index != friendIndex && Math.abs(Driver.userList.get(index).getAge() - Driver.userList.get(friendIndex).getAge()) <= 3){
			Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(friendIndex),"friends"));
		}else if(Driver.userList.get(index)instanceof YoungChild || Driver.userList.get(friendIndex)instanceof YoungChild){
			throw new TooYoungException("Young Child is too young to make friend");
		}else if(Math.abs(Driver.userList.get(index).getAge() - Driver.userList.get(friendIndex).getAge()) > 3){
			throw new NotToBeFriendException("The gap of years is more than 3");
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
			bw.write(Driver.userList.get(index).getName());
			bw.write(",");
			bw.write(Driver.userList.get(friendIndex).getName());
			bw.write(",");
			bw.write("friends");
			bw.newLine();
			bw.close();
			bw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
//	add classmate
	public void addClassmate(int index, int classmateIndex){
		if(!(Driver.userList.get(index)instanceof YoungChild) && !(Driver.userList.get(classmateIndex)instanceof YoungChild)){
			Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(classmateIndex),"classmate"));
		}else if(Driver.userList.get(index)instanceof YoungChild || Driver.userList.get(classmateIndex)instanceof YoungChild){
			throw new NotToBeClassmatesException("YoungChild can not add any classmate");
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
			bw.write(Driver.userList.get(index).getName());
			bw.write(",");
			bw.write(Driver.userList.get(classmateIndex).getName());
			bw.write(",");
			bw.write("classmate");
			bw.newLine();
			bw.close();
			bw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
//	add colleague
	public void addColleague(int index, int colleagueIndex){
		if(Driver.userList.get(index)instanceof Adult && Driver.userList.get(colleagueIndex)instanceof Adult){
			Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(colleagueIndex),"colleague"));
		}else if(!(Driver.userList.get(index)instanceof Adult) || !(Driver.userList.get(colleagueIndex)instanceof Adult)){
			throw new NotToBeColleaguesException("The user is under 17");
		}
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
			bw.write(Driver.userList.get(index).getName());
			bw.write(",");
			bw.write(Driver.userList.get(colleagueIndex).getName());
			bw.write(",");
			bw.write("colleague");
			bw.newLine();
			bw.close();
			bw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
//	add parent
	public void addParent(int index, int parentIndex){
		Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(parentIndex),"parent"));
		System.out.println("The parent is successfully added ");
		
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
			bw.write(Driver.userList.get(index).getName());
			bw.write(",");
			bw.write(Driver.userList.get(parentIndex).getName());
			bw.write(",");
			bw.write("parent");
			bw.newLine();
			bw.close();
			bw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
//	check whether they are sibling
	public void addSibling(int index, int siblingIndex){
		if(index != siblingIndex){
			Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(siblingIndex),"sibling"));
		}
	}
	
//	check if the this user can marry only when is an adult and not spouse 
	public boolean checkCanMarry(int index){
		boolean marry = false;
		boolean adult = false;
//		check whether this user has spouse
		for(int i = 0; i < Driver.connection.size(); i++){
			if(Driver.connection.get(i).toString().contains("couple") 
					&& Driver.connection.get(i).toString().contains(Driver.userList.get(index).getName())){
				marry = false;
				throw new NoAvailableException("This user has already married");
			}else{
				marry = true;
			}
		}
//		check whether this user is an adult
		if(Driver.userList.get(index) instanceof Adult){
				adult = true;
			}else{
				throw new  NotToBeCoupledException("This user is uder 17");
			}
		
		if(marry && adult){
			return true;
		}else{
			return false;
		}
	}
	
//	add spouse
	public void addSpouse(int index, int spouseshipIndex){
		if(this.checkCanMarry(index) && this.checkCanMarry(spouseshipIndex) 
				&& Driver.userList.get(index) != Driver.userList.get(spouseshipIndex)){
			Driver.connection.add(new Connection(Driver.userList.get(index),Driver.userList.get(spouseshipIndex),"couple"));
		}else{
			
		}
	}
	
//	relationship delete management
	public void relationshipDeleteMenu(int index){
		System.out.println("The relationship of your selected user is");
		System.out.println("Your selected user " + Driver.userList.get(index).getName());
		for(int i = 0; i < Driver.connection.size(); i++){
			if(Driver.connection.get(i).toString().contains(Driver.userList.get(index).getName())){
				System.out.println("Connection number: " + i + Driver.connection.get(i).toString());
			}
		}
		System.out.println("Please enter the number to select a person you want to remove from the connection");
		int deleteNumber = sc.nextInt();
		if(!(Driver.userList.get(index) instanceof Adult) && Driver.connection.get(deleteNumber).getRelation() == "parent"){
			throw new NoParentException("The user you want to remove is the parent of the selected user");
		}
	}
	
//	relationship management detail menu
	public void relationshipDetailAddMenu(int index){
		int relationshipDetail;
		int relationshipIndex;
		do{
		
			System.out.println("============Relationship Management Menu=========="
							+"\n 1.Add spouse"
							+"\n 2.Add sibling"
							+"\n 3.Add parent"
							+"\n 4.Add colleague"
							+"\n 5.Add classmate"
							+"\n 6.Add friend"
							+"\n 0.Go back"
					);
			relationshipDetail = sc.nextInt();
			switch(relationshipDetail){
				case 1:
						if(this.checkCanMarry(index)){
							do{
								System.out.println("Here is the user list, you can enter number to add spouse");
								this.listAllUsers();
								relationshipIndex = sc.nextInt();
								if(checkCanMarry(relationshipIndex)){
									this.addSpouse(index, relationshipIndex);
								}else{
									System.out.println("Please try again");
								}
							}while(!checkCanMarry(relationshipIndex));
						}
						break;
				case 2:
						System.out.println("Here is the user list, you can enter number to add sibling");
						this.listAllUsers();
						relationshipIndex = sc.nextInt();
						this.addSibling(index, relationshipIndex);
						break;
				case 3:
						boolean firstAdd = false;
						boolean secondAdd = false;
						do{
							System.out.println("Here is the user list, you can enter number to add the first parent");
							this.listAllUsers();
							relationshipIndex = sc.nextInt();
							if(this.checkIfExist(index, relationshipIndex)){
								this.addParent(index, relationshipIndex);
								firstAdd = true;
							}else{
								System.out.println("Please try again");
							}
						}while(firstAdd);
						
						do{
							System.out.println("Here is the user list, you can enter number to add the second parent");
							this.listAllUsers();
							relationshipIndex = sc.nextInt();
							if(this.checkIfExist(index, relationshipIndex)){
								this.addParent(index, relationshipIndex);
								secondAdd = true;
							}else{
								System.out.println("Please try again");
							}
						}while(secondAdd);
						break;
				case 4:
						do{
							System.out.println("Here is the user list, you can enter number to add the colleague");
							this.listAllUsers();
							relationshipIndex = sc.nextInt();
							if(this.checkIfExist(index, relationshipIndex)){
								this.addColleague(index, relationshipIndex);
							}else{
								System.out.println("Please try again");
							}
						}while(this.checkIfExist(index, relationshipIndex));
						break;
				case 5:
						do{
							System.out.println("Here is the user list, you can enter number to add the classmate");
							this.listAllUsers();
							relationshipIndex = sc.nextInt();
							if(this.checkIfExist(index, relationshipIndex)){
								this.addClassmate(index, relationshipIndex);
							}else{
								System.out.println("Please try again");
							}
						}while(this.checkIfExist(index, relationshipIndex));
						break;
				case 6:
						do{
							System.out.println("Here is the user list, you can enter number to add the friend");
							this.listAllUsers();
							relationshipIndex = sc.nextInt();
							if(this.checkIfExist(index, relationshipIndex)){
								this.addFriend(index, relationshipIndex);
							}else{
								System.out.println("Please try again");
							}
						}while(this.checkIfExist(index, relationshipIndex));
						break;
				case 0:
						break;
			}
		}while(relationshipDetail != 0);
	}
	


//	relationship management menu
	public void relationshipMenu(int index){
		int relationshipSelect;
		do{
			
			System.out.println("============Relationship Management Menu=========="
							+"\n 1.Add a relationship"
							+"\n 2.Remove a relationship"
							+"\n 0.Go back"
					);
			relationshipSelect = sc.nextInt();
			switch(relationshipSelect){
				case 1:
						this.relationshipDetailAddMenu(index);
						break;
				case 2:
						this.relationshipDeleteMenu(index);
						break;
				case 0:
						break;
			}
		}while(relationshipSelect != 0);
	}
	
//	user management menu
	public void managementMenu(int index){
		int managementSelect;
		do{
			
			System.out.println("Please enter the number to manage the user");
			System.out.println("==========User Management Menu==========="
							+"\n 1.View profile"
							+"\n 2.Update Information"
							+"\n 3.Manage relationship"
							+"\n 4.Remove from MiniNet"
							+"\n 0.Go back"
							
					);
			managementSelect = sc.nextInt();
			switch(managementSelect){
				case 1:
					this.displayProfile(index);
					break;
				case 2:
					this.updateInformation(index);
					break;
				case 3:
					this.relationshipMenu(index);
					break;
				case 4:
					this.deleteUser(index);
					break;
				case 0:
					break;
			}
		}while(managementSelect != 0);
		
		
	}
	
	public void mainMenu(){
		int select;
		do{
			System.out.println("============MiniNet Menu============" 
					+"\n 1.List all the users"
					+"\n 2.Search a user by name"
					+"\n 3.Add a user"
					+"\n 4.Check the relationship"
					+"\n 0.Exit");
			select = sc.nextInt();
			switch(select){
			case 1: 
					if(Driver.userList.isEmpty()){
						System.out.println("There is no any user, please try again");
					}else{
						System.out.println("All the users are shown below, please enter a number to select a user");
						this.listAllUsers();
						int index = sc.nextInt();
						this.managementMenu(index);
					}
					break;
			case 2:
					String searchName;
					do{
						searchName = sc.nextLine();
						int index = this.search(searchName);
						this.managementMenu(index);
					}while(this.search(searchName) == -1);
					break;
			case 3:
					System.out.println("Please enter your name");
					String name = sc.nextLine();
					name = sc.nextLine();
					System.out.println("Please enter your age");
					int age = sc.nextInt();
					System.out.println("Please enter your gender (M/F)");
					String g = sc.nextLine();
					g = sc.nextLine();
					char gender = g.charAt(0);
					System.out.println("Please enter your state");
					String image = sc.nextLine();
					System.out.println("Please enter your status, if you dont want to enter, just click enter");
					String status = sc.nextLine();
					System.out.println("Please upload your image, if you dont want to , just click enter");
					String state = sc.nextLine();
					if(age < 0 || age >150){
						throw new NoSuchAgeException("Please make sure you have entered a vaild age");
					}else{
						addUser(name, image, status, gender, age, state);
					}
					break;	
			case 4:
//					System.out.println("All the users are shown below, please enter a number to select a user");
//					this.listAllUsers();
//					int index = sc.nextInt();
//					System.out.println("The relationship of your selected user is");
//					System.out.println("Your selected user " + Driver.userList.get(index).getName());
					for(Connection c : Driver.connection){
							System.out.println(c.toString());
					}
					break;
			case 0:
					break;
			}
		}while(select != 0);
	}
	
	public static void readUser() throws IOException{
		
		try(
				BufferedReader br = new BufferedReader(new FileReader(new File("people.txt")));
				){
			String[] tempRead;
			String line = null;
			String name;
			String image;
			String status;
			char gender;
			int age;
			String state;
			while((line = br.readLine()) != null){
				tempRead = line.split(",");
					name = tempRead[0];
					image = tempRead[1];
					status = tempRead[2];
					gender = tempRead[3].charAt(0);
					age = Integer.parseInt(tempRead[4].replaceAll(" ", ""));
					state = tempRead[5];
					if(age >= 17 ){
						Driver.userList.add(new Adult(name, image, status, gender, age, state));
					}else if(age > 2 && age < 17){
						Driver.userList.add(new Child(name, image, status, gender, age, state));
					}else{
						Driver.userList.add(new YoungChild(name, image, status, gender, age, state));
					}
				
			}
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void relationReader() throws FileNotFoundException, IOException{
		try(
				BufferedReader br = new BufferedReader(new FileReader(new File("relations.txt")));
				){
			String line = null;
			String temp[];
			
			while((line = br.readLine()) != null){
				int index1 = 0;
				int index2 = 0;
				temp = line.split(", ");
				for(int i = 0; i < Driver.userList.size(); i++){
					if(temp[0].equals(Driver.userList.get(i).getName())){
						index1 = i;
						break;
					}
				}
				
				for(int i = 0; i < Driver.userList.size(); i++){
					if(temp[1].equals(Driver.userList.get(i).getName())){
						index2 = i;
						break;
					}
				}
				
				
				Driver.connection.add(new Connection(Driver.userList.get(index1),Driver.userList.get(index2),temp[2]));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
