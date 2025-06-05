package object;

import java.io.Serializable;
import java.util.List;

public class MsgSimple implements Serializable {

    private static final long serialVersionUID = 1L;

    private String operationName;

    private String channel;
    
    private List<Object> inParameter;
    
    private List<Object> outParameter;
    
    private String host;

    private boolean closeConnection;
    
    private boolean ack;
    
    public MsgSimple() {
    }

    
    
    public MsgSimple(String operationName, String channel, List<Object> inParameter, List<Object> outParameter, boolean closeConnection,String host) {
        this.operationName = operationName;
        this.channel = channel;
        this.inParameter = inParameter;
        this.outParameter = outParameter;
        this.closeConnection = closeConnection;
        this.host = host;
    }
    

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<Object> getInParameter() {
        return inParameter;
    }

    public void setInParameter(List<Object> inParameter) {
        this.inParameter = inParameter;
    }

    public List<Object> getOutParameter() {
        return outParameter;
    }

    public void setOutParameter(List<Object> outParameter) {
        this.outParameter = outParameter;
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isAck() {
        return ack;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    @Override
    public String toString(){
         return "Simple OperationName = "+getOperationName()+" ; Host = "+getHost()+" ; Channel = "+getChannel();

    }	

}