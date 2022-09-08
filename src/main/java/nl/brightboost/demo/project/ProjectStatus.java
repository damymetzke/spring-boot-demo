package nl.brightboost.demo.project;

public enum ProjectStatus {
    INVALID((byte)0),
    PROTOTYPE((byte)1),
    ALPHA((byte)2),
    BETA((byte)3),
    RELEASE((byte)4),
    ARCHIVED((byte)5);

    private byte value;

    private ProjectStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ProjectStatus fromValue(byte value) {
        switch (value) {
            case 1:
                return ProjectStatus.PROTOTYPE;
            case 2:
                return ProjectStatus.ALPHA;
            case 3:
                return ProjectStatus.BETA;
            case 4:
                return ProjectStatus.RELEASE;
            case 5:
                return ProjectStatus.ARCHIVED;
        }

        return ProjectStatus.INVALID;
    }

}
