package gui.swing;

public class ImageNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1261276834499806327L;
	private String message = "The Image attempted to be load couldn't be found.";
	
	public ImageNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
