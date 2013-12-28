package denoflionsx.denLib.CoreMod.ASM.SQL;

import denoflionsx.denLib.CoreMod.ASM.FileRequest;

public class SQLLibRequest extends FileRequest {

    public SQLLibRequest(String fileName, String url) {
        super(fileName, url);
    }

    public SQLLibRequest() {
        this("sqlite-jdbc-3.7.15-M1.jar", "https://github.com/denoflionsx/denLib/raw/master/lib/sqlite-jdbc-3.7.15-M1.zip");
    }
}
