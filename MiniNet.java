package miniNet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MiniNet extends Application {
	Scanner sc = new Scanner(System.in);
	Driver dr = new Driver();

	@Override
	public void start(Stage primaryStage) throws IOException {
		Driver.readUser();
		Driver.relationReader();
		try {
			GridPane sp = new GridPane();
			sp.setAlignment(Pos.CENTER);
			sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
			sp.setVgap(5.5);
			sp.setHgap(5.5);
			sp.add(new Text("MiniNet"), 0, 0);
			Button b1 = new Button("List all the user");
			Button b2 = new Button("Search by the name");
			Button b3 = new Button("Add a user");
			Button b4 = new Button("Check the relationship");
			b1.setOnAction((e) -> {
				this.list();
			});
			b2.setOnAction((e) -> {
				this.search();
			});
			b3.setOnAction((e) -> {
				this.add();
			});
			b4.setOnAction((e) -> {
				this.checkRelation();
			});

			sp.add(b1, 0, 1);
			sp.add(b2, 0, 2);
			sp.add(b3, 0, 3);
			sp.add(b4, 0, 4);

			Scene scene = new Scene(sp, 500, 250);
			primaryStage.setTitle("MiniNet");
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkRelation(){
		Stage checkRelation = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Label("Here is the user list, you can enter number to check relationship"), 0, 1);
		sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
		for (int i = 0; i < Driver.getUserList().size(); i++) {
			sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
		}
		sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
		TextField enter = new TextField();
		enter.setOnAction((e)->{
			int checkIndex = Integer.parseInt(enter.getText().trim());
			for(Connection c : Driver.getConnection()){
				if(c.toString().contains(Driver.getUserList().get(checkIndex).getName())){
					sp.add(new Label(c.toString()), 0, Driver.getUserList().size() + 6);
				}
			}
			
		});
		sp.add(enter, 1, Driver.getUserList().size() + 5);
		Scene scene = new Scene(sp, 500, 250);
		checkRelation.setScene(scene);
		checkRelation.show();
	}

	public void add() {
		
		try{
			Stage search = new Stage();
			GridPane sp = new GridPane();
			sp.setAlignment(Pos.CENTER);
			sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
			sp.setVgap(5.5);
			sp.setHgap(5.5);
			TextField enterName = new TextField();
			TextField enterImage = new TextField();
			TextField enterStatus = new TextField();
			TextField enterGender = new TextField();
			TextField enterAge = new TextField();
			TextField enterState = new TextField();
			Button sumit = new Button("sumit");
			if (Driver.getUserList().size() <= 500){
				sp.add(new Label("enter name here"), 0, 2);
				sp.add(enterName, 1, 2);
				sp.add(new Label("enter image here"), 0, 3);
				sp.add(enterImage, 1, 3);
				sp.add(new Label("enter status here"), 0, 4);
				sp.add(enterStatus, 1, 4);
				sp.add(new Label("enter gender here"), 0, 5);
				sp.add(enterGender, 1, 5);
				sp.add(new Label("enter age here"), 0, 6);
				sp.add(enterAge, 1, 6);
				sp.add(new Label("enter state here"), 0, 7);
				sp.add(enterState, 1, 7);
				sp.add(sumit, 3, 7);
				sumit.setOnAction((e) -> {
					String name = enterName.getText().trim();
					String image = enterImage.getText().trim();
					String status = enterStatus.getText().trim();
					char gender = (enterGender.getText().trim()).charAt(0);
					int age = Integer.parseInt(enterAge.getText().trim());
					String state = enterState.getText().trim();
					
					if((age < 2 && age > 0) || (age > 2 && age < 17)){
						//because it's a new user, so it doesn't have any dependence, so it need to add 2 parent
						Stage addFriend = new Stage();
						GridPane add = new GridPane();
						sp.setAlignment(Pos.CENTER);
						sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
						sp.setVgap(5.5);
						sp.setHgap(5.5);
						sp.add(new Label("Here is the user list, you can enter number to add friend"), 0, 1);
						sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
						for (int i = 0; i < Driver.getUserList().size(); i++) {
							sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
						}
						sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
						TextField enter1 = new TextField();
						TextField enter2 = new TextField();
						Button sumitP = new Button("sumit");
						sp.add(enter1, 0, Driver.getUserList().size() + 5);
						sp.add(enter2, 2, Driver.getUserList().size() + 5);
						sp.add(sumitP, 4, Driver.getUserList().size() + 5);
						final int[] pN= new int[2];
						do{
							sumitP.setOnAction((b)->{
								pN[1] = Integer.parseInt(enter1.getText().trim());
								pN[2] = Integer.parseInt(enter2.getText().trim());
							});
						}while(Driver.getUserList().get(pN[1])instanceof Adult && Driver.getUserList().get(pN[2])instanceof Adult);
						
						//find the parents
						Driver.getUserList().add(new User(name, image, status, gender, age, state));
						Driver.getConnection().add(new Connection(Driver.getUserList().get(Driver.getUserList().size()),Driver.getUserList().get(pN[1]),"parent"));
						Driver.getConnection().add(new Connection(Driver.getUserList().get(Driver.getUserList().size()),Driver.getUserList().get(pN[2]),"parent"));
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
						}catch(IOException a){
							a.printStackTrace();
						}
						
						
					}else if(age < 0 || age > 150){
						throw new NoSuchAgeException("Please make sure you have entered a vaild age"); 
					}else{
						Driver.getUserList().add(new User(name, image, status, gender, age, state));
						sp.add(new Label("enter state here"), 0, 9);
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
						}catch(IOException a){
							a.printStackTrace();
						}
					}
					
				});
				
				
//				the list is full
			}else{
				sp.add(new Label("Sorry, the user list is full, you can't add any user"), 0, 2);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		
}

	public void search() {
		try {
			Stage search = new Stage();
			GridPane sp = new GridPane();
			sp.setAlignment(Pos.CENTER);
			sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
			sp.setVgap(5.5);
			sp.setHgap(5.5);
			sp.add(new Label("Enter the name here"), 0, 0);
			TextField enter = new TextField();
			enter.setOnAction((e) -> {
				String name = enter.getText().trim();
				for (int i = 0; i < Driver.getUserList().size(); i++) {
					if (Driver.getUserList().get(i).getName().equals(name)) {
						sp.add(new Label("The user is found"), 0, 2);
						sp.add(new Label(Driver.getUserList().get(i).toString()), 0, 3);
						break;
					} else {
						sp.add(new Label("Sorry, we couldn't find this user, please try again"), 0, 3);
					}
				}
			});
			sp.add(enter, 0, 1);
			Scene scene = new Scene(sp, 500, 250);
			search.setTitle("MiniNet");
			search.setScene(scene);
			search.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void list() {
		try {
			Stage list = new Stage();
			GridPane sp = new GridPane();
			sp.setAlignment(Pos.CENTER);
			sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
			sp.setVgap(5.5);
			sp.setHgap(5.5);
			sp.add(new Label("User list"), 0, 0);
			if (Driver.getUserList().isEmpty()) {
				sp.add(new Label("There is no any user, please try again"), 0, 1);
			} else {
				sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 1);
				for (int i = 0; i < Driver.getUserList().size(); i++) {
					sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 2);
				}
				sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 2);
				TextField enter = new TextField();
				enter.setOnAction((e) -> {
					int index = Integer.parseInt(enter.getText().trim());
					this.managementMenu(index);
				});
				sp.add(enter, 1, Driver.getUserList().size() + 2);

			}
			Scene scene = new Scene(sp, 500, 250);
			list.setScene(scene);
			list.show();
			// dr.managementMenu(index);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deleteUser(int index){
		boolean delete = true;
		Stage deleteUser = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		if(Driver.getUserList().get(index)instanceof Adult){
			for(int i = 0; i < Driver.getConnection().size(); i++){
				if(Driver.getConnection().get(i).toString().contains(Driver.getUserList().get(index).getName())
						&& (Driver.getConnection().get(i).getRelationPerson1().getAge() < 17 ||Driver.getConnection().get(i).getRelationPerson2().getAge() < 17)
						&& Driver.getConnection().get(i).getRelation() == "parent"){
					delete = false;
					throw new NoParentException("You need to delet the child of this user first");
				}
			}
			if(delete){
				Driver.getUserList().remove(index);
				sp.add(new Label("Remove this user successfully"), 0, 1);
			}
		}else{
			Driver.getUserList().remove(index);
			System.out.println("Remove this user successfully");
		}
		Scene scene = new Scene(sp, 500, 250);
		deleteUser.setScene(scene);
		deleteUser.show();
	}
	
	public void relationshipDelete(int index){
		Stage relationshipDelete = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Label("Your selected user " + Driver.getUserList().get(index).getName()), 0, 0);
		sp.add(new Label("Here is the user list, you can enter number to delete a user"), 0, 1);
		sp.add(new Label("All the users are shown below, please enter a number"), 0, 2);
		for (int i = 0; i < Driver.getUserList().size(); i++) {
			sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
		}
		sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
		TextField enter = new TextField();
		enter.setOnAction((e)->{
			sp.add(new Label("Your selected user " + Driver.getUserList().get(index).getName()), 0, Driver.getUserList().size() + 5);
			int deleteNumber = Integer.parseInt(enter.getText().trim());
			if(!(Driver.getUserList().get(index) instanceof Adult) && Driver.getConnection().get(deleteNumber).getRelation() == "parent"){
				throw new NoParentException("The user you want to remove is the parent of the selected user");
			}else{
				Driver.getConnection().remove(deleteNumber);
			}
		});
		sp.add(enter, 1, Driver.getUserList().size() + 5);
		Scene scene = new Scene(sp, 500, 250);
		relationshipDelete.setScene(scene);
		relationshipDelete.show();
	}

	public void updateInformation(int index){
		Stage update = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		TextField enterName = new TextField();
		TextField enterImage = new TextField();
		TextField enterStatus = new TextField();
		TextField enterGender = new TextField();
		TextField enterAge = new TextField();
		TextField enterState = new TextField();
		Button sumit = new Button("sumit");
		sp.add(new Label("enter name here"), 0, 2);
		sp.add(enterName, 1, 2);
		sp.add(new Label("enter image here"), 0, 3);
		sp.add(enterImage, 1, 3);
		sp.add(new Label("enter status here"), 0, 4);
		sp.add(enterStatus, 1, 4);
		sp.add(new Label("enter gender here"), 0, 5);
		sp.add(enterGender, 1, 5);
		sp.add(new Label("enter age here"), 0, 6);
		sp.add(enterAge, 1, 6);
		sp.add(new Label("enter state here"), 0, 7);
		sp.add(enterState, 1, 7);
		sp.add(sumit, 3, 7);
		sumit.setOnAction((e) -> {
			String name = enterName.getText().trim();
			String image = enterImage.getText().trim();
			String status = enterStatus.getText().trim();
			char gender = (enterGender.getText().trim()).charAt(0);
			int age = Integer.parseInt(enterAge.getText().trim());
			String state = enterState.getText().trim();
			Driver.getUserList().get(index).setName(name);
			Driver.getUserList().get(index).setImage(image);
			Driver.getUserList().get(index).setStatus(status);
			Driver.getUserList().get(index).setGender(gender);
			Driver.getUserList().get(index).setAge(age);
			Driver.getUserList().get(index).setState(state);
			sp.add(new Label("The updated information is shown below"), 0, 8);
			sp.add(new Text(Driver.getUserList().get(index).toString()), 0, 9);
		});
		Scene scene = new Scene(sp, 500, 250);
		update.setScene(scene);
		update.show();
	}
	
	public void addCouple(int index){
		Stage addCouple = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		if(dr.checkCanMarry(index)){
				sp.add(new Label("Here is the user list, you can enter number to add spouse"), 0, 1);
				sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
				for (int i = 0; i < Driver.getUserList().size(); i++) {
					sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
				}
				sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
				TextField enter = new TextField();
				
				enter.setOnAction((e) -> {
					int spouseshipIndex = Integer.parseInt(enter.getText().trim());
					if(dr.checkCanMarry(spouseshipIndex) 
							&& Driver.getUserList().get(index) != Driver.getUserList().get(spouseshipIndex)){
						Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(spouseshipIndex),"couple"));
					}
						try{
							BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
							bw.write(Driver.getUserList().get(index).getName());
							bw.write(",");
							bw.write(Driver.getUserList().get(spouseshipIndex).getName());
							bw.write(",");
							bw.write("couple");
							bw.newLine();
							bw.close();
							bw.flush();
						}catch(IOException a){
							a.printStackTrace();
						}
				});
				sp.add(enter, 1, Driver.getUserList().size() + 5);
		}
		Scene scene = new Scene(sp, 500, 250);
		addCouple.setScene(scene);
		addCouple.show();
	}
	
	public void addParent(int index){
		boolean firstAdd = false;
		boolean secondAdd = false;
		Stage addParent = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		int parentCount = 0;
		for(Connection c : Driver.getConnection()){
			if(c.toString().contains("parent")){
				parentCount++;
			}
		}
		if(parentCount == 2){
			sp.add(new Label("This user already has two parents"), 0, 0);
			Scene scene = new Scene(sp, 500, 250);
			addParent.setScene(scene);
			addParent.show();
		}else if(parentCount == 1){
			
			sp.add(new Label("This user already has one parents"), 0, 0);
			sp.add(new Label("Here is the user list, you can enter number to add spouse"), 0, 1);
			sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
			for (int i = 0; i < Driver.getUserList().size(); i++) {
				sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
			}
			sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
			TextField enter = new TextField();
			enter.setOnAction((e)->{
			int parentIndex = Integer.parseInt(enter.getText().trim());
			Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(parentIndex),"parent"));
			
				try{
					BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
					bw.write(Driver.getUserList().get(index).getName());
					bw.write(",");
					bw.write(Driver.getUserList().get(parentIndex).getName());
					bw.write(",");
					bw.write("parent");
					bw.newLine();
					bw.close();
					bw.flush();
				}catch(IOException a){
					a.printStackTrace();
				}
			});
			sp.add(enter, 1, Driver.getUserList().size() + 5);
			
			Scene scene = new Scene(sp, 500, 250);
			addParent.setScene(scene);
			addParent.show();
			
			
		}else{
			Button sumit = new Button("sumit");
			sp.add(new Label("This user already has no parents"), 0, 0);
			sp.add(new Label("Here is the user list, you can enter number to add spouse"), 0, 1);
			sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
			for (int i = 0; i < Driver.getUserList().size(); i++) {
				sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
			}
			sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
			
			TextField enter = new TextField();
			TextField enter1 = new TextField();
			enter.setOnAction((e)->{
			int parentIndex = Integer.parseInt(enter.getText().trim());
			int parentIndex1 = Integer.parseInt(enter.getText().trim());
			Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(parentIndex),"parent"));
				try{
					BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
					bw.write(Driver.getUserList().get(index).getName());
					bw.write(",");
					bw.write(Driver.getUserList().get(parentIndex).getName());
					bw.write(",");
					bw.write("parent");
					bw.newLine();
					bw.write(Driver.getUserList().get(index).getName());
					bw.write(",");
					bw.write(Driver.getUserList().get(parentIndex1).getName());
					bw.write(",");
					bw.write("parent");
					bw.newLine();
					bw.close();
					bw.flush();
				}catch(IOException a){
					a.printStackTrace();
				}
			
			});
			
			sp.add(enter, 1, Driver.getUserList().size() + 5);
			sp.add(enter1, 2, Driver.getUserList().size() + 5);
			sp.add(sumit, 3, Driver.getUserList().size() + 5);
			Scene scene = new Scene(sp, 500, 250);
			addParent.setScene(scene);
			addParent.show();
		}
	}
	
	public void addFriend(int index){
		Stage addFriend = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Label("Here is the user list, you can enter number to add friend"), 0, 1);
		sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
		for (int i = 0; i < Driver.getUserList().size(); i++) {
			sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
		}
		sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
		TextField enter = new TextField();
		enter.setOnAction((e) -> {
			int friendIndex = Integer.parseInt(enter.getText().trim());
			
			if(Driver.getUserList().get(index)instanceof Adult && Driver.getUserList().get(friendIndex)instanceof Adult
					&& index != friendIndex){
				Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(friendIndex),"friends"));
			}else if(Driver.getUserList().get(index)instanceof Child && Driver.getUserList().get(friendIndex)instanceof Child
					&& index != friendIndex && Math.abs(Driver.getUserList().get(index).getAge() - Driver.getUserList().get(friendIndex).getAge()) <= 3){
				Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(friendIndex),"friends"));
			}else if(Driver.getUserList().get(index)instanceof YoungChild || Driver.getUserList().get(friendIndex)instanceof YoungChild){
				throw new TooYoungException("Young Child is too young to make friend");
			}else if(Math.abs(Driver.getUserList().get(index).getAge() - Driver.getUserList().get(friendIndex).getAge()) > 3){
				throw new NotToBeFriendException("The gap of years is more than 3");
			}
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
				bw.write(Driver.getUserList().get(index).getName());
				bw.write(",");
				bw.write(Driver.getUserList().get(friendIndex).getName());
				bw.write(",");
				bw.write("friends");
				bw.newLine();
				bw.close();
				bw.flush();
			}catch(IOException a){
				a.printStackTrace();
			}
		});
		sp.add(enter, 1, Driver.getUserList().size() + 5);
		Scene scene = new Scene(sp, 500, 250);
		addFriend.setScene(scene);
		addFriend.show();
	}
	
	
	public void addClassmate(int index){
		
		Stage addClassmate = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Label("Here is the user list, you can enter number to add classmate"), 0, 1);
		sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
		for (int i = 0; i < Driver.getUserList().size(); i++) {
			sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
		}
		sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
		TextField enter = new TextField();
		enter.setOnAction((e) -> {
			int classmateIndex = Integer.parseInt(enter.getText().trim());
			if(!(Driver.getUserList().get(index)instanceof YoungChild) && !(Driver.getUserList().get(classmateIndex)instanceof YoungChild)){
				Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(classmateIndex),"classmate"));
			}else if(Driver.getUserList().get(index)instanceof YoungChild || Driver.getUserList().get(classmateIndex)instanceof YoungChild){
				throw new NotToBeClassmatesException("YoungChild can not add any classmate");
			}
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
				bw.write(Driver.getUserList().get(index).getName());
				bw.write(",");
				bw.write(Driver.getUserList().get(classmateIndex).getName());
				bw.write(",");
				bw.write("classmate");
				bw.newLine();
				bw.close();
				bw.flush();
			}catch(IOException a){
				a.printStackTrace();
			}
		});
		sp.add(enter, 1, Driver.getUserList().size() + 5);
		Scene scene = new Scene(sp, 500, 250);
		addClassmate.setScene(scene);
		addClassmate.show();
		
		
	}
	
	public void addColleague(int index){
		Stage addColleague = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Label("Here is the user list, you can enter number to add colleague"), 0, 1);
		sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
		for (int i = 0; i < Driver.getUserList().size(); i++) {
			sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
		}
		sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
		TextField enter = new TextField();
		enter.setOnAction((e) -> {
			int colleagueIndex = Integer.parseInt(enter.getText().trim());
			if(Driver.getUserList().get(index)instanceof Adult && Driver.getUserList().get(colleagueIndex)instanceof Adult){
				Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(colleagueIndex),"colleague"));
			}else if(!(Driver.getUserList().get(index)instanceof Adult) || !(Driver.getUserList().get(colleagueIndex)instanceof Adult)){
				throw new NotToBeColleaguesException("The user is under 17");
			}
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
				bw.write(Driver.getUserList().get(index).getName());
				bw.write(",");
				bw.write(Driver.getUserList().get(colleagueIndex).getName());
				bw.write(",");
				bw.write("colleague");
				bw.newLine();
				bw.close();
				bw.flush();
			}catch(IOException a){
				a.printStackTrace();
			}
		});
		sp.add(enter, 1, Driver.getUserList().size() + 5);
		Scene scene = new Scene(sp, 500, 250);
		addColleague.setScene(scene);
		addColleague.show();
		
	}
	
	public void addSibling(int index){
		Stage addSibling = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Label("Here is the user list, you can enter number to add sibling"), 0, 1);
		sp.add(new Label("All the users are shown below, please enter a number to select a user"), 0, 2);
		for (int i = 0; i < Driver.getUserList().size(); i++) {
			sp.add(new Label(i + " : " + Driver.getUserList().get(i).getName()), 0, i + 3);
		}
		sp.add(new Label("Enter the number here"), 0, Driver.getUserList().size() + 4);
		TextField enter = new TextField();
		
		enter.setOnAction((e) -> {
			int siblingIndex = Integer.parseInt(enter.getText().trim());
			Driver.getConnection().add(new Connection(Driver.getUserList().get(index),Driver.getUserList().get(siblingIndex),"sibling"));
				try{
					BufferedWriter bw = new BufferedWriter(new FileWriter("Relations.txt"));
					bw.write(Driver.getUserList().get(index).getName());
					bw.write(",");
					bw.write(Driver.getUserList().get(siblingIndex).getName());
					bw.write(",");
					bw.write("sibling");
					bw.newLine();
					bw.close();
					bw.flush();
				}catch(IOException a){
					a.printStackTrace();
				}
		});
		sp.add(enter, 1, Driver.getUserList().size() + 5);
		Scene scene = new Scene(sp, 500, 250);
		addSibling.setScene(scene);
		addSibling.show();
	}
	
	public void relationAddMenu(int index){
		
		Stage relationshipMenu = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Text("Relationship Management Menu"), 0, 0);
		Button b1 = new Button("Add spouse");
		Button b2 = new Button("Add sibling");
		Button b3 = new Button("Add parent");
		Button b4 = new Button("Add colleague");
		Button b5 = new Button("Add classmate");
		Button b6 = new Button("Add friend");
		b1.setOnAction((e) -> {
			this.addCouple(index);
		});
		b2.setOnAction((e) -> {
			this.addSibling(index);
		});
		b3.setOnAction((e) -> {
			this.addParent(index);
		});
		b4.setOnAction((e) -> {
			this.addColleague(index);
		});
		b5.setOnAction((e) -> {
			this.addClassmate(index);
		});
		b6.setOnAction((e) -> {
			this.addFriend(index);
		});
		sp.add(b1, 0, 1);
		sp.add(b2, 0, 2);
		sp.add(b3, 0, 3);
		sp.add(b4, 0, 4);
		sp.add(b5, 0, 5);
		sp.add(b6, 0, 6);
		Scene scene = new Scene(sp, 800, 250);
		relationshipMenu.setScene(scene);
		relationshipMenu.show();
	}
	
	
	
	public void relationshipMenu(int index){
		
		Stage relationshipMenu = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Text("Relationship Management Menu"), 0, 0);
		Button b1 = new Button("Add a relationship");
		Button b2 = new Button("Remove a relationship");
		b1.setOnAction((e) -> {
			this.relationAddMenu(index);
		});
		b2.setOnAction((e) -> {
			this.relationshipDelete(index);
		});
		
		sp.add(b1, 0, 1);
		sp.add(b2, 0, 2);
		Scene scene = new Scene(sp, 500, 250);
		relationshipMenu.setScene(scene);
		relationshipMenu.show();
	}
	
	public void managementMenu(int index) {
		Stage managementMenu = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Text("Management Menu"), 0, 0);
		Button b1 = new Button("View the profile");
		Button b2 = new Button("Update Information");
		Button b3 = new Button("Manage relationship");
		Button b4 = new Button("Remove from MiniNet");
		b1.setOnAction((e) -> {
			this.showProfile(index);
		});
		b2.setOnAction((e) -> {
			this.updateInformation(index);
		});
		b3.setOnAction((e) -> {
			this.relationshipMenu(index);
		});
		b4.setOnAction((e) -> {
			this.deleteUser(index);
		});

		sp.add(b1, 0, 1);
		sp.add(b2, 0, 2);
		sp.add(b3, 0, 3);
		sp.add(b4, 0, 4);
		Scene scene = new Scene(sp, 500, 250);
		managementMenu.setScene(scene);
		managementMenu.show();
	}

	public void showProfile(int index) {
		Stage managementMenu = new Stage();
		GridPane sp = new GridPane();
		sp.setAlignment(Pos.CENTER);
		sp.setPadding(new Insets(11.5, 11.5, 11.5, 11.5));
		sp.setVgap(5.5);
		sp.setHgap(5.5);
		sp.add(new Text("Show profile"), 0, 0);
		sp.add(new Text("Personal profile"), 0, 1);
		sp.add(new Text(Driver.getUserList().get(index).toString()), 0, 2);
		Scene scene = new Scene(sp, 500, 250);
		managementMenu.setScene(scene);
		managementMenu.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
