package denoflionsx.denLib.CoreMod.ASM.SQL;

import denoflionsx.denLib.CoreMod.ASM.FileRequest;

public class SQLLibRequest extends FileRequest {

    public SQLLibRequest(String fileName, String url) {
        super(fileName, url);
    }

    public SQLLibRequest() {
        this("sqlite-jdbc-3.7.15-M1.jar", "https://dl.dropboxusercontent.com/u/23892866/Downloads/sqlite-jdbc-3.7.15-M1.zip");
    }
}
