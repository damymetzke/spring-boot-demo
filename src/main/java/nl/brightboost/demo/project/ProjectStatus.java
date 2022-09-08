package nl.brightboost.demo.project;

public enum ProjectStatus {
    INVALID((byte)0),
    PROTOTYPE((byte)10),
    ALPHA((byte)2),
    BETA((byte)3),
    RELEASE((byte)4);

    private byte value;

    private ProjectStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ProjectStatus fromValue(byte value) {
        switch (value) {
            case 10:
                return ProjectStatus.PROTOTYPE;
            case 2:
                return ProjectStatus.ALPHA;
            case 3:
                return ProjectStatus.BETA;
            case 4:
                return ProjectStatus.RELEASE;
        }

        return ProjectStatus.INVALID;
    }

}
