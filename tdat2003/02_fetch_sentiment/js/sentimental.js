(function () { // TODO find out if there is any need for this
"use strict";

const moods = [
    { mood: 'terrible',     color: '#502332' },
    { mood: 'awful',        color: '#E74D3B' },
    { mood: 'okay',         color: '#FBA446' },
    { mood: 'good',         color: '#DCD762' },
    { mood: 'magnificent',  color: '#82C6B5' }
];

const URL = 'http://bigdata.stud.iie.ntnu.no/sentiment/webresources/sentiment?api-key=Happy%21%21%21';
// api-key does not seem to be necessary after all, wow
// note that in urls ! is encoded as %21, Chrome compained but Firefox did not.

// encountered another error now, https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS/Errors/CORSMissingAllowOrigin
// seems like it's caused by the service being down and sending a response that does not include the correct CORS header(s)
// (ignore this now that the service is up&running)
// (didn't need to change url btw)

const body = document.getElementsByTagName("body").item(0);
const messageLabel = document.getElementById("sendme");

/**
 * Changes the appearance and content of the webpage
 * based on the input sentence's mood
 *
 * @param code Mood code, received from fetchSentiment()
 */
const change = code => {
    if (code < 0 || code > 4) return;
    messageLabel.innerHTML = `You're feeling <strong>${moods[code].mood}</strong> right now.`;
    body.style.backgroundColor = moods[code].color;
};

/**
 * To JSON or not to JSON,
 * that is the question.
 * Fetches response from REST service
 *
 * @param sentence NOT NULL PLEASE
 * @returns {Promise<Response>} JSON-ified
 */
const fetchSentiment = sentence => {
    if (sentence === null || sentence === '')
        throw new Error('Sentence string is empty/null');

    let data = {
        sentence: sentence
    };

    return fetch(URL, {
        method: 'POST', // or 'PUT'
        header: new Headers({
            'Content-Type': 'application/json; charset=utf-8'
        }),
        body: JSON.stringify(data)
    }).then(res => {
        if (res.ok) return res.json();
        else throw new Error('Response was not okay');
    });
};

const button = document.getElementById("sentence-button");
const input = document.getElementById("sentence-input");

// thanks to https://stackoverflow.com/a/45650898
document.addEventListener("keyup", event => {
    if(event.key !== "Enter") return; // Use `.key` instead.
    button.click(); // Things you want to do.
    event.preventDefault(); // No need to `return false
});

button.addEventListener("click", evt => {
    let value = input.value;
    console.log(value);
    if (value === "") return;

    fetchSentiment(value)
    .then(response => {
        let moodCode = JSON.stringify(response);
        console.log('sentence was very:', moodCode);
        change(moodCode);
        //console.log("We have " + mood);
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

// Filling boxes with colors for debugging
// (could've done this stuff as CSS classes but I change the HTML's text anyway)
let boxes = document.getElementsByClassName('debug-option');

for (let i = 0; i < boxes.length; i++) {
    const box = boxes[i];
    box.style.backgroundColor = moods[i].color;
    box.addEventListener('click', evt => change(i));
}


// giving the user a damn error message if test goes wrong
let errorField = document.getElementById("error");

fetchSentiment("i am very sad")
.then(response => {
    errorField.innerText = 'The service is up & running';
    console.log('Test gave us ' + JSON.stringify(response));
})
.catch(error => {
    console.error('Error:', error);
    errorField.innerText = 'Servicen har litt vondt i magen atm (Service down)';
    if (typeof InstallTrigger !== 'undefined') { // is firefox, https://stackoverflow.com/a/9851769
        errorField.innerHTML += '<br> Firefox: Try disabling mixed content protection <strong>for this page</strong>.';
    }
});

})();
