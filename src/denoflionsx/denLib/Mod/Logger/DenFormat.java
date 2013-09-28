package denoflionsx.denLib.Mod.Logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class DenFormat extends SimpleFormatter {

    @Override
    public synchronized String format(LogRecord record) {
        if (record.getLevel() == Level.INFO) {
            return record.getMessage() + "\r\n";
        } else {
            return super.format(record);
        }
    }
}
