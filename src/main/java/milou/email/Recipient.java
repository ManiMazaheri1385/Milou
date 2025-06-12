package milou.email;

import jakarta.persistence.*;

@Entity
@Table(name = "Recipients")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id")
    private Email email;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Basic(optional = false)
    @Column(name = "is_read")
    private Boolean isRead;

    public Recipient() {}

    public Recipient(Email email, User recipient, Boolean isRead) {
        setEmail(email);
        setRecipient(recipient);
        setRead(isRead);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

}
