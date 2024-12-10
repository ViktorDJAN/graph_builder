package ru.promelectronika.dto;

public class SentParamDto {
    private int startStop;//
    private float ev_u;//
    private float ev_i;//
    private float ev_maxU;//
    private float ev_maxI;//
    private float ev_maxP;//
    private int timeCharge;//
    private int cp_on;//
    private int err_code;//
    private int ready;//
    private int soc;//
    private int contactorStatus;//
    private int protocol;//

    public SentParamDto() {
    }

    public SentParamDto(int startStop,
                        float ev_u,
                        float ev_i,
                        float ev_maxU,
                        float ev_maxI,
                        float ev_maxP,
                        int timeCharge,
                        int cp_on,
                        int err_code,
                        int ready,
                        int soc,
                        int contactorStatus,
                        int protocol) {
        this.startStop = startStop;
        this.ev_u = ev_u;
        this.ev_i = ev_i;
        this.ev_maxU = ev_maxU;
        this.ev_maxI = ev_maxI;
        this.ev_maxP = ev_maxP;
        this.timeCharge = timeCharge;
        this.cp_on = cp_on;
        this.err_code = err_code;
        this.ready = ready;
        this.soc = soc;
        this.contactorStatus = contactorStatus;
        this.protocol = protocol;
    }

    //Data: 00 00 00 00 = 0             ===      0, 0, 0, 0,
    //      00 00 48 43  =200           ===      0, 0, 72, 67,
    //      00 00 a0 40  =5             ===      0, 0, -96, 64,
    //      00 80 3b 44  =750           ===      0, -128, 59, 68,
    //      00 00 fa 43  =500           ===      0, 0, -6, 67,
    //      00 00 96 43  =300           ===      0, 0, -106, 67,
    //      58 02 00 00  = 600 int      ===      88, 2, 0, 0,
    //      01 00 00 00  = 1   int      ===      1, 0, 0, 0,
    //      00 00 00 00  =0    int      ===      0, 0, 0, 0,
    //      01 00 00 00  =1    int      ===      1, 0, 0, 0,
    //      33 00 00 00  =51   int      ===      51, 0, 0, 0,
    //      00 00 00 00  =0    int      ===      0, 0, 0, 0,
    //      00 00 00 00  =0    int      ===      0, 0, 0, 0

    public int getStartStop() {
        return startStop;
    }

    public void setStartStop(int startStop) {
        this.startStop = startStop;
    }

    public float getEv_u() {
        return ev_u;
    }

    public void setEv_u(float ev_u) {
        this.ev_u = ev_u;
    }

    public float getEv_i() {
        return ev_i;
    }

    public void setEv_i(float ev_i) {
        this.ev_i = ev_i;
    }

    public float getEv_maxU() {
        return ev_maxU;
    }

    public void setEv_maxU(float ev_maxU) {
        this.ev_maxU = ev_maxU;
    }

    public float getEv_maxI() {
        return ev_maxI;
    }

    public void setEv_maxI(float ev_maxI) {
        this.ev_maxI = ev_maxI;
    }

    public float getEv_maxP() {
        return ev_maxP;
    }

    public void setEv_maxP(float ev_maxP) {
        this.ev_maxP = ev_maxP;
    }

    public int getTimeCharge() {
        return timeCharge;
    }

    public void setTimeCharge(int timeCharge) {
        this.timeCharge = timeCharge;
    }

    public int getCp_on() {
        return cp_on;
    }

    public void setCp_on(int cp_on) {
        this.cp_on = cp_on;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    public int getContactorStatus() {
        return contactorStatus;
    }

    public void setContactorStatus(int contactorStatus) {
        this.contactorStatus = contactorStatus;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "SentParamDto{" +
                "startStop=" + startStop +
                ", ev_u=" + ev_u +
                ", ev_i=" + ev_i +
                ", ev_maxU=" + ev_maxU +
                ", ev_maxI=" + ev_maxI +
                ", ev_maxP=" + ev_maxP +
                ", timeCharge=" + timeCharge +
                ", cp_on=" + cp_on +
                ", err_code=" + err_code +
                ", ready=" + ready +
                ", soc=" + soc +
                ", contactorStatus=" + contactorStatus +
                ", protocol=" + protocol +
                '}';
    }
}
