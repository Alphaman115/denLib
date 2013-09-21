package denoflionsx.denLib.CoreMod.ASM.SQL;

import denoflionsx.denLib.CoreMod.denLibCore;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.common.ForgeVersion;

public class SQLiteASM implements IClassTransformer {

    private String targetClass;
    private String targetClassDeobf;
    private File db;

    public SQLiteASM(String targetClass, String targetClassDeobf, File db) {
        this.targetClass = targetClass;
        this.targetClassDeobf = targetClassDeobf;
        this.db = db;
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        if (string.equals(targetClass)) {
            try {
                Connection connection = createDB(db.getAbsolutePath());
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + targetClassDeobf + " where VERSION=" + String.valueOf(ForgeVersion.getBuildVersion()));
                String hash = rs.getString(1);
                rs = statement.executeQuery("select * from " + targetClassDeobf + "PatchData" + " where HASH=" + hash);
                byte[] patch = rs.getBytes(1);
                if (patch != null) {
                    denLibCore.print("Found patch data for Forge " + String.valueOf(ForgeVersion.getBuildVersion()) + " in " + db + "!");
                    rs.close();
                    statement.close();
                    connection.close();
                    return patch;
                } else {
                    denLibCore.print("No patch data found for Forge " + String.valueOf(ForgeVersion.getBuildVersion()) + "! Go politely ask den to run the db updater!");
                    rs.close();
                    statement.close();
                    connection.close();
                }
            } catch (Throwable t) {
                denLibCore.print("Something went wrong when trying to patch " + targetClass + "!");
                t.printStackTrace();
            }
        }
        return bytes;
    }

    public static Connection createDB(String dbName) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (Throwable t) {
        }
        return null;
    }
}
