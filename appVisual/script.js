// parameters

let startStop;
let ev_u;
let ev_i;
let ev_maxU;
let ev_maxI;
let ev_maxP;
let timeCharge;
let cp_on;
let err_code;
let ready;
let soc;
let contactorStatus;
let protocol;


let checkBoxEv_I = document.getElementById("cb_ev_i");
let checkBoxEv_U = document.getElementById("cb_ev_u");
let checkBoxEVSE_I = document.getElementById("cb_evse_i");

let checkBoxEVSE_U = document.getElementById("cb_evse_u");
let checkBoxCP_U = document.getElementById("cb_cp_u");
let checkBoxCPFreq = document.getElementById("cb_cp_freq");
let checkBoxCPDutyCycle = document.getElementById("cb_cp_duty_cycle");
let checkBoxDtMsg = document.getElementById("cb_dt_msg");
let checkBoxStage = document.getElementById("cb_stage");




function getDataXMLHttpRequest() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://127.0.0.1:3060/get_data', false);
    xhr.setRequestHeader('Content-Type', 'text/plain');
    xhr.send();
    // custom it in accordance of terms
    const jsonString = xhr.response;

    var param = JSON.parse(xhr.response);
    // console.log(param)
    // console.log(param.sentData.evse_U)

    const jsonObj = new Function('return ' + jsonString)(); // string to json obj
    // console.log(jsonObj)
    // console.log(jsonObj["sentData"]["ev_u"])


    try {
        // builds horizontal line
        if (jsonObj["sentData"] !== null || jsonObj["sentData"] !== undefined) {
            // building graphs with outgoing params  __________________________
            buildGraphAndCB("evse_U", dataSetEVSE_U, jsonObj["sentData"],checkBoxEVSE_U);
            buildGraphAndCB("evse_I", dataSetEVSE_I, jsonObj["sentData"],checkBoxEVSE_I);
            buildGraphAndCB("cp_U", dataSetCP_U, jsonObj["sentData"],checkBoxCP_U);
            buildGraphAndCB("cp_Freq", dataSetCP_Freq, jsonObj["sentData"],checkBoxCPFreq);
            buildGraphAndCB("cp_DutyCicle", dataSetCP_DutyCycle, jsonObj["sentData"],checkBoxCPDutyCycle);
            buildGraphAndCB("dt_message", dataSetDt_message, jsonObj["sentData"],checkBoxDtMsg);
            buildGraphAndCB("stage", dataSetStage, jsonObj["sentData"],checkBoxStage);
        }
        if (jsonObj["receivedData"] !== null || jsonObj["receivedData"] !== undefined) {
            buildGraphAndCB("ev_u", dataSetEV_U, jsonObj["receivedData"],checkBoxEv_U);
            buildGraphAndCB("ev_i", dataSetEV_I, jsonObj["receivedData"],checkBoxEv_I);
        }
        if (jsonObj["receivedData"] !== null || jsonObj["sentData"] !== null) {
            buildAxisX();
        }

    } catch (e) {
        console.log('Empty object')
    }

}



setInterval(getDataXMLHttpRequest, 1000);



// time delay
const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));
// await sleep(500);
let counter = 0;


// GRAPH BUILDERS
async function buildGraphAndCB(paramName, dataSet, array,checkBoxState) {
    if (checkBoxState.checked===true) {
    buildGraph(paramName, dataSet, array)
    }
}

async function buildGraph(paramName, dataSet, array) {
    let param;
    if (array !== null && array !== undefined) {
        param = array[paramName];
        let number = await transform(param);
        if (!isNaN(number)) {
            console.log(number + "N")
            dataSet.data.push(number);
        }
        chart1.update();
    }
}

async function buildAxisX() {
    chart1.data.labels.push(counter);
    counter++;
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
        data: [],
        borderWidth: 1
    }
}


// Mind it with especial carefulness!
let chart1 = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [],
        datasets: [dataSetEV_U, dataSetEV_I, dataSetEVSE_U, dataSetEVSE_I, dataSetCP_U,
            dataSetCP_Freq, dataSetCP_DutyCycle, dataSetStage, dataSetDt_message]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
})
