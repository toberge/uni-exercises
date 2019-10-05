const myButton = document.querySelector("#myButton");
const notYourButton = document.getElementById('notYourButton');
const body = document.getElementsByTagName('body')[0];

// ctrl+r localStorage
sessionStorage.setItem('token', null);

// TODO as a reminder, it's headers not header

const getTokenHeader = () => {
  return new Headers({
    'x-access-token': sessionStorage.getItem('token')
  })
};

const refreshToken = () => {
  fetch('token', {
    method: "get",
    headers: getTokenHeader()
  })
    .then(response => response.json())
    .then(json => {
      if (json.jwt) sessionStorage.setItem('token', json.jwt);
    })
    .catch(error => console.error("Error: ", error));
};

const fetchPerson = () => {
  let url = "api/person/1";
  fetch(url, {
    method: "GET",
    headers: getTokenHeader()
  })
    .then(response => response.json())
    .then(json => {
      console.log(JSON.stringify(json));
      if (json.name) {
        body.style.backgroundColor = '#2DA95C';
      } else {
        body.style.backgroundColor = '#1D544B';
      }
    })
    .catch(error => console.error("Error: ", error));
};

myButton.addEventListener("click", e => {
  console.log("Fikk klikk-event");
  refreshToken();
  fetchPerson();
});

notYourButton.addEventListener("click", e => {
  console.log("Fikk klikk-event OG ER KJIP");
  // no refresh
  fetchPerson();
});

const passwordField = document.querySelector('*[type="password"]');
const usernameField = document.querySelector('*[type="text"]');
const loginButton = document.getElementById('login');

loginButton.addEventListener('click', e => {
  if (!(passwordField.value && usernameField.value)) return console.error('invalid input');

  fetch('login', {
    method: "POST",
    headers: new Headers({
      'Content-Type': 'application/json; charset=utf-8'
    }),
    body: JSON.stringify({
      brukernavn: usernameField.value,
      passord: passwordField.value
    })
  })
    .then(response => response.json())
    .then(json => {
      if (json.jwt) sessionStorage.setItem('token', json.jwt);
    })
    .catch(error => console.error("Error: ", error));
});
