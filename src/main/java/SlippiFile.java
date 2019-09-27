import com.devsmart.ubjson.UBArray;

public class SlippiFile {
    private byte[] data;
    private Metadata metadata;


    public SlippiFile(byte[] raw, Metadata meta) {
        data = raw;
        metadata = meta;
    }
    public SlippiFile(UBArray raw, Metadata meta) {
        byte[] dataHolder = new byte[raw.size()];
        for (int i = 0; i < raw.size(); i++)
            dataHolder[i] = raw.get(i).asByte();

        metadata = meta;
    }
}
