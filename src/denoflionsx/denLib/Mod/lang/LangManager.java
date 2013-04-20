package denoflionsx.denLib.Mod.lang;

import cpw.mods.fml.common.registry.LanguageRegistry;
import java.io.*;
import java.util.Properties;

public class LangManager {
    
    public File _configFolder;
    public String modid;
    
    public LangManager(String modid, File configDir) {
        this.modid = modid;
        this.setConfigFolderBase(configDir);
    }
    
    public final void setConfigFolderBase(File folder) {
        _configFolder = new File(folder.getAbsolutePath() + "/" + getConfigBaseFolder() + "/" + this.modid + "/");
    }
    
    public String getConfigBaseFolder() {
        return "denoflionsx";
    }
    
    public void extractLang(String[] languages) {
        String langResourceBase = "/" + getConfigBaseFolder() + "/" + modid + "/Lang/";
        for (String lang : languages) {
            InputStream is = this.getClass().getResourceAsStream(langResourceBase + lang + ".lang");
            try {
                OutputStream os = new FileOutputStream(_configFolder.getAbsolutePath() + "/" + lang + ".lang");
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                is.close();
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void loadLang() {
        for (File langFile : _configFolder.listFiles(new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".lang");
            }
        })) {
            try {
                Properties langPack = new Properties();
                langPack.load(new FileInputStream(langFile));
                String lang = langFile.getName().replace(".lang", "");
                LanguageRegistry.instance().addStringLocalization(langPack, lang);
            } catch (FileNotFoundException x) {
                x.printStackTrace();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
    }
}
