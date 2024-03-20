package GUI;

public class OutputResponse extends ButtonResponse {

    public OutputResponse() {
        super();
        //this.Active =false;
    }

    public void activate() {
        this.Active = !this.Active;
        if (this.Active) {
            System.out.println("activated");
        } else {
            System.out.println("disactivated");

        }
    }

}
