package denoflionsx.denLib.CoreMod.ASM.SQL;

import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.common.ForgeVersion;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteASM implements IClassTransformer {

    private String targetClassDeobf;
    private File db;

    public SQLiteASM(String targetClassDeobf, File db) {
        this.targetClassDeobf = targetClassDeobf;
        this.db = db;
    }

    // For continued 1.6.2 compat.
    public SQLiteASM(String bullshit, String targetClassDeobf, File db) {
        this.targetClassDeobf = targetClassDeobf;
        this.db = db;
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        // Lock to vanilla classes.
        if (!string1.toLowerCase().contains("net.minecraft")) {
            return bytes;
        }
        String[] a = string1.split("\\.");
        if (a[a.length - 1].equals(targetClassDeobf)) {
            ArrayList<String> versions = new ArrayList();
            try {
                Connection connection = denLib.SQLHelper.createDB(db.getAbsolutePath());
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + targetClassDeobf);
                String hash = "Nope";
                denLibCore.print("Searching database " + db.getName() + " for forge version " + String.valueOf(ForgeVersion.getBuildVersion()) + ". Target Class: " + this.targetClassDeobf);
                while (rs.next()) {
                    versions.add(rs.getString(1));
                    if (rs.getString(1).equals(String.valueOf(ForgeVersion.getBuildVersion()))) {
                        hash = rs.getString(2);
                    }
                }
                ResultSet rs2 = statement.executeQuery("select * from " + targetClassDeobf + "PatchData");
                byte[] patch = null;
                while (rs2.next()) {
                    if (rs2.getString(1).equals(hash)) {
                        patch = rs2.getBytes(2);
                    }
                }
                if (patch != null) {
                    denLibCore.print("Found patch data for Forge " + String.valueOf(ForgeVersion.getBuildVersion()) + " in " + db.getName() + "!");
                    rs.close();
                    statement.close();
                    connection.close();
                    return patch;
                } else {
                    denLibCore.print("No patch data found for Forge " + String.valueOf(ForgeVersion.getBuildVersion()) + "! Go politely ask den to run the db updater!");
                    String supported = "";
                    denLibCore.print("This db supports the following versions of Forge:");
                    for (String s : versions) {
                        supported += (s + ",");
                    }
                    supported = supported.substring(0, supported.lastIndexOf(",") - 1);
                    denLibCore.print(supported);
                    rs.close();
                    statement.close();
                    connection.close();
                }
            } catch (Throwable t) {
                denLibCore.print("Something went wrong when trying to patch " + targetClassDeobf + "!");
                t.printStackTrace();
            }
        }
        return bytes;
    }
}
