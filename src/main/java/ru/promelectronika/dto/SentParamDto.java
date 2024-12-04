package ru.promelectronika.dto;

public class SentParamDto {
    private float evse_U;
    private float evse_I;
    private float evse_maxU;
    private float evse_maxI;
    private float evse_maxP;
    private float CP_U;
    private float CP_Freq;
    private float CP_DutyCicle;
    private float DT_message;
    private int stage;
    private int contactorRequest;

    public SentParamDto() {
    }

    public SentParamDto(float evse_U,
                        float evse_I,
                        float evse_maxU,
                        float evse_maxI,
                        float evse_maxP,
                        float CP_U,
                        float CP_Freq,
                        float CP_DutyCicle,
                        float DT_message,
                        int stage,
                        int contactorRequest) {
        this.evse_U = evse_U;
        this.evse_I = evse_I;
        this.evse_maxU = evse_maxU;
        this.evse_maxI = evse_maxI;
        this.evse_maxP = evse_maxP;
        this.CP_U = CP_U;
        this.CP_Freq = CP_Freq;
        this.CP_DutyCicle = CP_DutyCicle;
        this.DT_message = DT_message;
        this.stage = stage;
        this.contactorRequest = contactorRequest;
    }

    public float getEvse_U() {
        return evse_U;
    }

    public void setEvse_U(float evse_U) {
        this.evse_U = evse_U;
    }

    public float getEvse_I() {
        return evse_I;
    }

    public void setEvse_I(float evse_I) {
        this.evse_I = evse_I;
    }

    public float getEvse_maxU() {
        return evse_maxU;
    }

    public void setEvse_maxU(float evse_maxU) {
        this.evse_maxU = evse_maxU;
    }

    public float getEvse_maxI() {
        return evse_maxI;
    }

    public void setEvse_maxI(float evse_maxI) {
        this.evse_maxI = evse_maxI;
    }

    public float getEvse_maxP() {
        return evse_maxP;
    }

    public void setEvse_maxP(float evse_maxP) {
        this.evse_maxP = evse_maxP;
    }

    public float getCP_U() {
        return CP_U;
    }

    public void setCP_U(float CP_U) {
        this.CP_U = CP_U;
    }

    public float getCP_Freq() {
        return CP_Freq;
    }

    public void setCP_Freq(float CP_Freq) {
        this.CP_Freq = CP_Freq;
    }

    public float getCP_DutyCicle() {
        return CP_DutyCicle;
    }

    public void setCP_DutyCicle(float CP_DutyCicle) {
        this.CP_DutyCicle = CP_DutyCicle;
    }

    public float getDT_message() {
        return DT_message;
    }

    public void setDT_message(float DT_message) {
        this.DT_message = DT_message;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getContactorRequest() {
        return contactorRequest;
    }

    public void setContactorRequest(int contactorRequest) {
        this.contactorRequest = contactorRequest;
    }

    @Override
    public String toString() {
        return "SentParamsDto{" +
                "evse_U=" + evse_U +
                ", evse_I=" + evse_I +
                ", evse_maxU=" + evse_maxU +
                ", evse_maxI=" + evse_maxI +
                ", evse_maxP=" + evse_maxP +
                ", CP_U=" + CP_U +
                ", CP_Freq=" + CP_Freq +
                ", CP_DutyCicle=" + CP_DutyCicle +
                ", DT_message=" + DT_message +
                ", stage=" + stage +
                ", contactorRequest=" + contactorRequest +
                '}';
    }
}
