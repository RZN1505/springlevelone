package pro.bolshakov.geekbrains.dz6.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sells")
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private Date date = new Date();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clients_id")
    private Client client;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_sells",
            joinColumns = @JoinColumn(name = "sell_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;
    @Column
    private Long amount = 1L;
    @Column
    private Double price = 0d;

    public Sell() {
    }

    public Sell(Long id, Client client) {
        this.id = id;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Client getClient() {
        System.out.println(client);
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public void setProducts(List<Product> products) {
        this.productList = products;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        setPrice();
        this.amount = amount;
    }

    public void setPrice() {
        Double priceHelp = 0d;
        for (Product itemProduct : productList) {
            Double priceProduct = itemProduct.getPrice();
            System.out.println("itemProduct" + itemProduct);
            priceHelp = priceHelp + amount * priceProduct;
            System.out.println("this.price" + this.price);

        }
        this.price = priceHelp;
    }
    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sell sell = (Sell) o;
        return Objects.equals(id, sell.id) &&
                Objects.equals(client, sell.client) &&
                Objects.equals(price, sell.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, price);
    }

    @Override
    public String toString() {
        return "Sell{" +
                "id=" + id +
                ", client='" + client.getName() + '\'' +
                ", price=" + getPrice() +
                '}';
    }
}
