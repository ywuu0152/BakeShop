package entity;

public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private String tfn;
    private String address;
    private String role;
    private String storeId;
    private String phoneNumber;
    private String postal;


    public User() {
    }

    public User(String id, String username, String email, String password, String tfn, String address, String role,
                String storeId, String phoneNumber, String postal) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.tfn = tfn;
        this.address = address;
        this.role = role;
        this.storeId = storeId;
        this.phoneNumber = phoneNumber;
        this.postal = postal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTfn() {
        return tfn;
    }

    public void setTfn(String tfn) {
        this.tfn = tfn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tfn='" + tfn + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", storeNumber='" + storeId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", postal='" + postal + '\'' +
                '}';
    }
}