let arrayWithResponses = [];

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

    const jsonObj = new Function('return ' + jsonString)();
    // console.log(jsonObj)
   // console.log(jsonObj["sentData"]["ev_u"])


    try {
        buildGr(); // builds horizontal line
        if (jsonObj["sentData"]!== null || jsonObj["sentData"] !== undefined) {
            // building graphs with outgoing params  ___________________________
            console.log(jsonObj["sentData"])
            buildGraph("evse_U", dataSetEVSE_U, jsonObj["sentData"]);
            buildGraph("evse_I", dataSetEVSE_I, jsonObj["sentData"]);
            buildGraph("cp_U", dataSetCP_U, jsonObj["sentData"]);
            buildGraph("cp_Freq", dataSetCP_Freq, jsonObj["sentData"]);
            buildGraph("cp_DutyCicle", dataSetCP_DutyCycle, jsonObj["sentData"]);
            buildGraph("dt_message", dataSetDt_message, jsonObj["sentData"]);
            buildGraph("stage", dataSetStage, jsonObj["sentData"]);
        }
        if (jsonObj["receivedData"]!== null || jsonObj["receivedData"] !== undefined) {
            buildGraph("ev_u", dataSetEV_U, jsonObj["receivedData"]);
            buildGraph("ev_i", dataSetEV_I, jsonObj["receivedData"]);

        }

        // if (jsonObj["receivedData"] !== null || jsonObj["receivedData"] !== undefined) {
        //     // console.log("EVERYTHING iS OK" + Object.keys(jsonObj[1]))
        //     buildGraph("evse_U", dataSetEVSE_U, jsonObj["receivedData"]);
        //     buildGraph("evse_I", dataSetEVSE_I, jsonObj["receivedData"]);
        //     buildGraph("cp_U", dataSetCP_U, jsonObj["receivedData"]);
        //     buildGraph("cp_Freq", dataSetCP_Freq, jsonObj["receivedData"]);
        //     buildGraph("cp_DutyCicle", dataSetCP_DutyCycle, jsonObj["receivedData"]);
        //     buildGraph("dt_message", dataSetDt_message, jsonObj["receivedData"]);
        //     buildGraph("stage", dataSetStage, jsonObj["receivedData"]);
        //
        // }












            // console.log(ev_u + '__EV_U_ PARAM')
            // ev_i = jsonObj[0]["ev_i"];   // build graph
            // ev_maxU = jsonObj[0]["ev_maxU"];
            // ev_maxI = jsonObj[0]["ev_maxI"];
            // ev_maxP = jsonObj[0]["ev_maxP"];
            // timeCharge = jsonObj[0]["timeCharge"];
            // cp_on = jsonObj[0]["cp_on"];
            // err_code = jsonObj[0]["err_code"];
            // ready = jsonObj[0]["ready"];
            // soc = jsonObj[0]["soc"];
            // contactorStatus = jsonObj[0]["contactorStatus"];
            // protocol = jsonObj[0]["protocol"];

    } catch (e) {
        console.log('Empty object')
    }


    // // getting incoming parameters ___________________________
    // let startStop = jsonObj[0]["startStop"];
    // let ev_u = jsonObj[0]["ev_u"];   // build graph
    // let ev_i = jsonObj[0]["ev_i"];   // build graph
    // let ev_maxU = jsonObj[0]["ev_maxU"];
    // let ev_maxI = jsonObj[0]["ev_maxI"];
    // let ev_maxP = jsonObj[0]["ev_maxP"];
    // let timeCharge = jsonObj[0]["timeCharge"];
    // let cp_on = jsonObj[0]["cp_on"];
    // let err_code = jsonObj[0]["err_code"];
    // let ready = jsonObj[0]["ready"];
    // let soc = jsonObj[0]["soc"];
    // let contactorStatus = jsonObj[0]["contactorStatus"];
    // let protocol = jsonObj[0]["protocol"];

    // // getting outgoing parameters
    // let evse_U = jsonObj[0]["evse_U"];    // build graph
    // let evse_I = jsonObj[0]["evse_I"];    // build graph
    // let evse_maxU = jsonObj[0]["evse_maxU"];
    // let evse_maxI = jsonObj[0]["evse_maxI"];
    // let evse_maxP = jsonObj[0]["evse_maxP"];
    // let stage = jsonObj[0]["stage"];     // build graph
    // let contactorRequest = jsonObj[0]["contactorRequest"];
    // let cp_U = jsonObj[0]["cp_U"];                  // build graph
    // let cp_DutyCicle = jsonObj[0]["cp_DutyCicle"];  // build graph
    // let cp_Freq = jsonObj[0]["cp_Freq"];        // build graph
    // let dt_message = jsonObj[0]["dt_message"];  // build graph

}

setInterval(getDataXMLHttpRequest, 1000);


async function parseResponse(paramIndex) {
    if (arrayWithResponses.length === 0) {
        setTimeout(100)
        // console.log('THERE IS NULL')
    } else {
        let paramValue = arrayWithResponses.shift();
        // console.log(paramValue + "IT RETURNED" + arrayWithResponses.length)
        let arrayWithParams = paramValue.split("__");
        return arrayWithParams[paramIndex];

    }


}


const sleep = ms => new Promise(resolve => setTimeout(resolve, ms));
let counter = 0;

// GRAPH BUILDERS

async function buildGraph(paramName, dataSet, array) {
    let param;
    // console.log(paramName)
    // console.log(array)
    if(array!==null && array!==undefined){
        param = array[paramName];

        let number = await transform(param);
        if (!isNaN(number)) {
            console.log(number + "N")
            dataSet.data.push(number);
    }
    // if (array[paramName] !== null && array[paramName] !== undefined) {
    //     param = array[paramName];
    // }
    // let number = await transform(param);
    // if (!isNaN(number)) {
    //     dataSet.data.push(number);
    //
  }


}

async function buildGr() {
    // Задержка в 0.5 секунды перед выводом сообщения


    chart1.data.labels.push(counter);
    counter++;
    chart1.update();

    // await sleep(500);
}


async function transform(data) {
    let a = Number(data);
    if (!isNaN(a) || a !== undefined)
        return a;

}


// setInterval(buildGraph, 500);


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


let dataSetA = {
    label: 'JavaScript Developer',
    lineTension: 0,
    backgroundColor: 'blue',
    borderColor: 'blue',
    data: [],
    borderWidth: 1
};


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
