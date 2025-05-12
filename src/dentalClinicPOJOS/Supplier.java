package dentalClinicPOJOS;

import java.io.Serializable;
import java.util.Objects;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 869952558751098968L;
    private Integer supplier_id;
    private String name;
    private String surname;
    private Integer phone;
    private String email;
    private String address;
    private Material material; 

    // Constructor con parámetros
    public Supplier(String name, String surname, Integer phone, String email, String address, Material material) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.material = material;
    }

    // Getter para supplier_id
    public Integer getSupplier_id() {
        return supplier_id;
    }

    // Setters y Getters restantes...
    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, email, material, name, phone, supplier_id, surname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Supplier other = (Supplier) obj;
        return Objects.equals(address, other.address) && Objects.equals(email, other.email)
                && Objects.equals(material, other.material) && Objects.equals(name, other.name)
                && Objects.equals(phone, other.phone) && supplier_id == other.supplier_id
                && Objects.equals(surname, other.surname);
    }

    @Override
    public String toString() {
        return "Supplier [name=" + name + ", surname=" + surname + ", phone=" + phone + ", email=" + email
                + ", address=" + address + ", material=" + material + "]";
    }
}
