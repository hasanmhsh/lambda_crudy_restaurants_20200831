package com.lambdaschool.crudyrestaurants.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The entity allowing interaction with the restaurants table.
 */
@Entity
@Table(name = "restaurants")
@JsonIgnoreProperties(value = "hasvalueforseatcapacity") // never comes in from client!
public class Restaurant
{
    /**
     * The primary key number (long) of the restaurants table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long restaurantid;

    /**
     * The name (String) of the restaurant. Cannot be null and must be unique.
     */
    @Column(unique = true,
            nullable = false)
    private String name;

    /**
     * The address (String) of the restaurant. Any format is acceptable.
     */
    private String address;

    /**
     * The city (String) of the restaurant. Any format is acceptable.
     */
    private String city;

    /**
     * The state (String) of the restaurant. The format is the two character abbreviation of the state.
     */
    private String state;

    /**
     * The telephone number (String) of the restaurant. Any format is acceptable.
     */
    private String telephone;

    /**
     * Used to determine if the field seatcapacity has been set or is NULL, meaning 0 for an integer value.
     * Does not get saved to the database.
     */
    @Transient
    public boolean hasvalueforseatcapacity = false;

    /**
     * The seating capacity (integer) of the restaurant.
     * This was added to specifically show how to update fields that do not have a NULL value.
     */
    private int seatcapacity;

    /**
     * Creates a join table joining Restaurants and Payments in a Many-To-Many relations.
     * Contains a Set of Payment Objects used by this restaurant.
     */
    @ManyToMany()
    @JoinTable(name = "restaurantpayments",
            joinColumns = @JoinColumn(name = "restaurantid"),
            inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties("restaurants")
    Set<Payment> payments = new HashSet<>();

    /**
     * List of menus associated with this restaurant. Does not get saved in the database directly.
     * Forms a One-To-Many relationship to menus. One restaurant to many menus.
     */
    @OneToMany(mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties("restaurant")
    private List<Menu> menus = new ArrayList<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Restaurant()
    {
    }

    /**
     * Given the params, create a new restaurant object.
     * <p>
     * restaurantid is autogenerated.
     *
     * @param name         The name (String) of the restaurant.
     * @param address      The address (String) of the restaurant.
     * @param city         The city (String) of the restaurant.
     * @param state        The state (String) of the restaurant.
     * @param telephone    The telephone number (String) of the restaurant.
     * @param seatcapacity The seating capacity (Integer) of the restaurant.
     *                     menus are added outside of this constructor.
     */
    public Restaurant(
            String name,
            String address,
            String city,
            String state,
            String telephone,
            int seatcapacity)
    {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.telephone = telephone;
        this.seatcapacity = seatcapacity;
    }

    /**
     * Getter for restaurantid.
     *
     * @return The primary key number (long) of the restaurant's table.
     */
    public long getRestaurantid()
    {
        return restaurantid;
    }

    /**
     * Setter for the restaurantid - used primarily when seeding data.
     *
     * @param restaurantid The new primary key number (long) of the restaurants table.
     */
    public void setRestaurantid(long restaurantid)
    {
        this.restaurantid = restaurantid;
    }

    /**
     * Getter for name.
     *
     * @return The name (String) of the Restaurant.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name The new name (String) of the Restaurant.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Getter for address.
     *
     * @return The address (String) of the Restaurant.
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Setter for address
     *
     * @param address The new address (String) for the Restaurant.
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Getter for city
     *
     * @return The city (String) where the restaurant is located.
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Setter for city
     *
     * @param city The new city (String) of the restaurant.
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Getter for the state.
     *
     * @return The state (String) of the current restaurant.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Getter for the state.
     *
     * @param state The new state (String) of the restaurant.
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Getter for the telephone.
     *
     * @return The telephone number (String) of the current restaurant.
     */
    public String getTelephone()
    {
        return telephone;
    }

    /**
     * Setter for the telephone.
     *
     * @param telephone The new telephone number (String) for the restaurant.
     */
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    /**
     * Getter for seatcapacity.
     *
     * @return How many (integer) seats this restaurant has.
     */
    public int getSeatcapacity()
    {
        return seatcapacity;
    }

    /**
     * Setter for seatcapacity.
     * <p>
     * If the value is set through the JPA, specifically through a JSON object set to this API,
     * hasvaluefor will be set to true. Otherwise it defaults to false.
     * This allows the application to tell if a seatcapacity is 0 because it was set as 0 or
     * because it is not set and thus should be considered NULL but is in fact 0.
     *
     * @param seatcapacity The new amount (integer) of seats this restaurant has.
     */
    public void setSeatcapacity(int seatcapacity)
    {
        hasvalueforseatcapacity = true;
        this.seatcapacity = seatcapacity;
    }

    /**
     * Getter for Payments.
     *
     * @return The set of Payments used by this restaurant.
     */
    public Set<Payment> getPayments()
    {
        return payments;
    }

    /**
     * Setter for Payments.
     *
     * @param payments A new list of Payments to be used by this restaurant.
     */
    public void setPayments(Set<Payment> payments)
    {
        this.payments = payments;
    }

    /**
     * Getter for menus.
     *
     * @return A List of menus associated with the current restaurant.
     */
    public List<Menu> getMenus()
    {
        return menus;
    }

    /**
     * Setter for menus.
     *
     * @param menus A new list of menus for this restaurant.
     */
    public void setMenus(List<Menu> menus)
    {
        this.menus = menus;
    }
}