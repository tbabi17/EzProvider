package mn.ezpay.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "miniToken",
                query = "SELECT * FROM token WHERE timestampdiff(SECOND,_date,current_timestamp) < :time_out",
                resultClass = token.class
        ),
        @NamedNativeQuery(
                name = "expiredTokens",
                query = "DELETE FROM token WHERE timestampdiff(SECOND,_date,current_timestamp) > :time_out and status = 0",
                resultClass = token.class
        ),
        @NamedNativeQuery(
                name = "token5",
                query = "select * FROM multitoken where token=:token",
                resultClass = multitoken.class
        )
})
@Table(name = "token")
@Entity
public class token implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String token;
    @Column
    private int status;
    @Column
    private double amount;
    @Column
    private String merchantData;
    @Column
    private String _date;
    @Column
    private String response;
    @Column
    private String walletId;
    @Column
    private String hashed;
    @Column
    private String traceNo;
    @Column
    private String type;
    @Column
    private String merchantId;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<purchase> trace;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "merchantId", referencedColumnName = "merchantId", insertable = false, updatable = false)
    private merchant_only merchant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMerchantData() {
        return merchantData;
    }

    public void setMerchantData(String merchantData) {
        this.merchantData = merchantData;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getHashed() {
        return hashed;
    }

    public void setHashed(String hashed) {
        this.hashed = hashed;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<purchase> getTrace() {
        return trace;
    }

    public void setTrace(List<purchase> trace) {
        this.trace = trace;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public merchant_only getMerchant() {
        return merchant;
    }

    public void setMerchant(merchant_only merchant) {
        this.merchant = merchant;
    }
}
