package denoflionsx.denLib.Interfaces;

public interface IDenProxy {
    
    public String preloadTextures(String texture);
    
    public void sendMessageToPlayer(String msg);
    
    public void print(String msg);
    
    public String getConfigDir();
    
}
