package sample.MainScreenWin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Suraj on 5/3/2017.
 */
public class TableClass {


    IntegerProperty id = new SimpleIntegerProperty(0);
    StringProperty name = new SimpleStringProperty("");
    StringProperty company = new SimpleStringProperty("");
    StringProperty address = new SimpleStringProperty("");
    StringProperty purpose = new SimpleStringProperty("");
    StringProperty time = new SimpleStringProperty("");
    StringProperty date = new SimpleStringProperty("");
    StringProperty nationality = new SimpleStringProperty("");
    StringProperty phone = new SimpleStringProperty("");
    StringProperty user = new SimpleStringProperty("");


    public TableClass(int id, String name, String company, String address,
                      String purpose, String time, String date, String nationality, String phone, String user) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.company = new SimpleStringProperty(company);
        this.address = new SimpleStringProperty(address);
        this.purpose = new SimpleStringProperty(purpose);
        this.time = new SimpleStringProperty(time);
        this.date = new SimpleStringProperty(date);
        this.nationality = new SimpleStringProperty(nationality);
        this.phone = new SimpleStringProperty(phone);
        this.user= new SimpleStringProperty(user);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setUser(String user){
        this.user.set(user);
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPurpose(String purpose) {
        this.purpose.set(purpose);
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public String getUser(){
        return user.get();
    }

    public StringProperty userProperty(){return user;}

    public StringProperty nameProperty() {
        return name;
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPurpose() {
        return purpose.get();
    }

    public StringProperty purposeProperty() {
        return purpose;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }
}
