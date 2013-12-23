package denoflionsx.denLib.Mod.Logger;

import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class DenFormat extends SimpleFormatter {

    @Override
    public synchronized String format(LogRecord record) {
        return record.getMessage() + "\r\n";
    }
}
