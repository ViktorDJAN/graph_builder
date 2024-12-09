// parameters
let startStop_Btn = document.getElementById("startStopBtn");
let cp_Btn = document.getElementById("cpBtn");
let connect_Btn = document.getElementById("connectBtn");
let discharge_Btn = document.getElementById("dischargeBtn");
let reset_Btn = document.getElementById("resetBtn");
let save_Btn = document.getElementById("saveBtn");


let ev_u_Field = document.getElementById("ev_u");
let ev_i_Field = document.getElementById("ev_i");
let ev_maxU_Field = document.getElementById("ev_maxU");
let ev_maxI_Field = document.getElementById("ev_maxI");
let ev_maxP_Field = document.getElementById("ev_maxP");
let timeCharge_Field = document.getElementById("timecharge ");
let err_code_Field = document.getElementById("selectErrorCodeId");
let ready_Field = document.getElementById("selectReady");
let soc_Field = document.getElementById("soc");
let protocol_Field = document.getElementById("selectProtocolId");
let evse_maxI_Field = document.getElementById("evse_maxI");
let evse_maxU_Field = document.getElementById("evse_maxU");
let evse_maxP_Field = document.getElementById("evse_maxP");
let evse_I_Field = document.getElementById("evse_I");
let evse_U_Field = document.getElementById("evse_U");
let stage_Field = document.getElementById("stage");

let contactorStatus_Indicator = document.getElementById("indicatorContactor");
let stage_Indicator1 = document.getElementById("indicatorStage1");
let stage_Indicator2 = document.getElementById("indicatorStage2");
let stage_Indicator3 = document.getElementById("indicatorStage3");
let stage_Indicator4 = document.getElementById("indicatorStage4");


let checkBoxEv_I = document.getElementById("cb_ev_i");
let checkBoxEv_U = document.getElementById("cb_ev_u");
let checkBoxEVSE_I = document.getElementById("cb_evse_i");
let checkBoxEVSE_U = document.getElementById("cb_evse_u");
let checkBoxCP_U = document.getElementById("cb_cp_u");
let checkBoxCPFreq = document.getElementById("cb_cp_freq");
let checkBoxCPDutyCycle = document.getElementById("cb_cp_duty_cycle");
let checkBoxDtMsg = document.getElementById("cb_dt_msg");
let checkBoxStage = document.getElementById("cb_stage");

////////////////////////////////////////////////////////////////////////////////////////
const sentParamDto = {
    startStop: 0,
    ev_u: 0,
    ev_i: 0,
    ev_maxU: 0,
    ev_maxI: 0,
    ev_maxP: 0,
    timeCharge: 0,
    cp_on: 0,
    err_code: 0,
    ready: 0,
    soc: 0,
    contactorStatus: 0,
    protocol: 0
}

const receivedParamDto = {
    evse_u: 0,
    evse_i: 0,
    evse_maxU: 0,
    evse_maxI: 0,
    evse_maxP: 0,
    cp_u: 0,
    cp_freq: 0,
    cp_dutyCycle: 0,
    dt_message: 0,
    stage: 0,
    contactorRequest: 0
}



// mapping gotten data to object so as, to give out from it values to the dom-components
function parseReceivedDataToDto(arrayWithReceivedData) {
    const receivedParamDto = {
        evse_u: arrayWithReceivedData["evse_U"],
        evse_i: arrayWithReceivedData["evse_I"],
        evse_maxU: arrayWithReceivedData["evse_maxU"],
        evse_maxI: arrayWithReceivedData["evse_maxI"],
        evse_maxP: arrayWithReceivedData["evse_maxP"],
        cp_u: arrayWithReceivedData["cp_U"],
        cp_freq: arrayWithReceivedData["cp_Freq"],
        cp_dutyCycle: arrayWithReceivedData["cp_DutyCicle"],
        dt_message: arrayWithReceivedData["dt_message"],
        stage: arrayWithReceivedData["stage"],
        contactorRequest: arrayWithReceivedData["contactorRequest"]
    }
    console.log(receivedParamDto)
    return receivedParamDto;
}

const giveOutDataFromReceivedDtoToDomComponents = (dto) => {
    evse_U_Field.value = dto.evse_u;
    evse_I_Field.value = dto.evse_i;
    evse_maxU_Field.value = dto.evse_maxU;
    evse_maxI_Field.value = dto.evse_maxI;
    evse_maxP_Field.value = dto.evse_maxP;
    stage_Field.value = dto.stage;
    contactorStatus_Indicator.value = dto.contactorStatus;
}

const gatherDomComponentsDataToSentParamObj = () => {
    const sentParamDto = {
        startStop: startStop_Btn.value,
        ev_u: ev_u_Field.value,
        ev_i: ev_i_Field.value,
        ev_maxU: ev_maxU_Field.value,
        ev_maxI: ev_maxI_Field.value,
        ev_maxP: ev_maxP_Field.value,
        timeCharge: timeCharge_Field.value,
        cp_on: cp_Btn.value,
        err_code: err_code_Field.value,
        ready: ready_Field.value,
        soc: soc_Field.value,
        contactorStatus: contactorsStatus,
        protocol: protocol_Field.value
    }
    console.log(protocol_Field.value)
   return sentParamDto;


}


let counter = 0;

function getDataXMLHttpRequest() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://127.0.0.1:3060/getDataFromJava', false);
    xhr.setRequestHeader('Content-Type', 'text/plain');
    xhr.send();
    const jsonString = xhr.response;


    const jsonObj = new Function('return ' + jsonString)(); // string to json obj
    let receivedDto;

    try { // increases our x-axis
        console.log(jsonObj["sentData"])
        if ((jsonObj["sentData"] !== undefined && jsonObj["sentData"] !== null) ||
            (jsonObj["receivedData"] !== undefined && jsonObj["receivedData"] !== null)) {
            chart1.data.labels.push(new Date());// increases our x-axis
        }


        if (jsonObj["sentData"] !== null && jsonObj["sentData"] !== undefined) {
            // building graphs with outgoing params  __________________________
            buildGraphBaseOnCheckBoxesStates("ev_u", dataSetEV_U, jsonObj["sentData"], checkBoxEv_U);
            buildGraphBaseOnCheckBoxesStates("ev_i", dataSetEV_I, jsonObj["sentData"], checkBoxEv_I);

        }
        if (jsonObj["receivedData"] !== null && jsonObj["receivedData"] !== undefined) {
            receivedDto = parseReceivedDataToDto(jsonObj["receivedData"]);
            giveOutDataFromReceivedDtoToDomComponents(receivedDto)
            buildGraphBaseOnCheckBoxesStates("evse_U", dataSetEVSE_U, jsonObj["receivedData"], checkBoxEVSE_U);
            buildGraphBaseOnCheckBoxesStates("evse_I", dataSetEVSE_I, jsonObj["receivedData"], checkBoxEVSE_I);
            buildGraphBaseOnCheckBoxesStates("cp_U", dataSetCP_U, jsonObj["receivedData"], checkBoxCP_U);
            buildGraphBaseOnCheckBoxesStates("cp_Freq", dataSetCP_Freq, jsonObj["receivedData"], checkBoxCPFreq);
            buildGraphBaseOnCheckBoxesStates("cp_DutyCicle", dataSetCP_DutyCycle, jsonObj["receivedData"], checkBoxCPDutyCycle);
            buildGraphBaseOnCheckBoxesStates("dt_message", dataSetDt_message, jsonObj["receivedData"], checkBoxDtMsg);
            buildGraphBaseOnCheckBoxesStates("stage", dataSetStage, jsonObj["receivedData"], checkBoxStage);
        }

    } catch (e) {
        console.log('Empty object')
    }

}

function startCharge() {
    if (startStop_Btn.value === "0") {
        startStop_Btn.value = "1";
        console.log("start btn pressed: " + startStop_Btn.value)
    } else {
        startStop_Btn.value = "0";
        console.log("start btn unpressed: " + startStop_Btn.value);
    }
}

function toggleCp() {
    if (cp_Btn.value === "0") {
        cp_Btn.value = "1";
        console.log("cp on: " + cp_Btn.value)
    } else {
        cp_Btn.value = "0";
        console.log("cp off: " + cp_Btn.value);
    }
}

let contactorsStatus = "0"; //todo: To move variable up
function dischargeContactors() {
    if (contactorsStatus === "0") {
        contactorsStatus = "1";
        console.log("contactors locked: " + contactorsStatus)
    }
}
function resetContactors(){
    if(contactorsStatus==="1"){
        contactorsStatus="0";
        console.log("contactors unlocked: " + contactorsStatus)

    }
}



//sending data to java
function postChosenParamsToJava(){
    var xhr = new XMLHttpRequest();
    xhr.open("POST","http://127.0.0.1:3060/postDataToJava",false);
    xhr.setRequestHeader('Content-Type', 'text/plain');
    xhr.send(JSON.stringify(gatherDomComponentsDataToSentParamObj()));

}

setInterval(postChosenParamsToJava, 1000);
setInterval(getDataXMLHttpRequest, 1000);


// time delay
const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));
// await sleep(500);


// GRAPH BUILDERS
async function buildGraphBaseOnCheckBoxesStates(paramName, dataSet, array, checkBoxState) {
    if (checkBoxState.checked === true) {
        buildGraph(paramName, dataSet, array)
    }
}

async function buildGraph(paramName, dataSet, array) {
    let param;
    if (array !== null && array !== undefined) {
        param = array[paramName];
        let number = await transform(param);
        if (!isNaN(number)) {
            dataSet.data.push(number);
        }
        chart1.update();
    }
}

function buildAxisX() {
    chart1.data.labels.push(counter);
    counter++;
    console.log(counter)

}


async function transform(data) {
    let a = Number(data);
    if (!isNaN(a) || a !== undefined)
        return a
}


let dataSetEV_U = createDataSet("EV_U", "#a8acfd", "#a8acfd");
let dataSetEVSE_U = createDataSet("EVSE_U", "#004891", "#004891");
let dataSetEV_I = createDataSet("EV_I", "#6fff6f", "#6fff6f");
let dataSetEVSE_I = createDataSet("EVSE_I", "#008000", "#008000");
let dataSetCP_U = createDataSet("CP_U", "#ff8000", "#ff8000");
let dataSetCP_Freq = createDataSet("CP_Freq", "#ffb76f", "#ffb76f");
let dataSetCP_DutyCycle = createDataSet("CP_DutyCycle", "#b05800", "#b05800");
let dataSetDt_message = createDataSet("Dt_message", "#ff00ff", "#ff00ff");
let dataSetStage = createDataSet("Stage", "#ff0000", "#ff0000");


const ctx = document.getElementById('myChart');

function createDataSet(label, backgroundColor, borderColor) {
    return {
        label: label,
        lineTension: 0,
        backgroundColor: backgroundColor,
        borderColor: borderColor,
        fill: false,
        data: [],
        borderWidth: 1
    }
}


const config = {
    type: 'line',
    data: {
        labels: [],
        datasets: [dataSetEV_U, dataSetEV_I, dataSetEVSE_U, dataSetEVSE_I, dataSetCP_U,
            dataSetCP_Freq, dataSetCP_DutyCycle, dataSetStage, dataSetDt_message]
    },
    options: {
        scales: {
            xAxes: [{
                type: 'time',
            },],
        },
        pan: {
            enabled: true,
            mode: 'xy',
        },
        zoom: {
            enabled: true,
            mode: 'xy', // or 'x' for "drag" version
        },
    },
};

var chart1;
window.onload = function () {
    chart1 = new Chart(document.getElementById('chart'), config);
};

