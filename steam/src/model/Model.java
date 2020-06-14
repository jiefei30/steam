package model;
public class Model {
	//Ä£ĞÍ²ã³ĞÔØµÄÃ½½é
	private Games games = new Games();
	private Users users = new Users();
	public Games getGames() {
		return games;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
}
