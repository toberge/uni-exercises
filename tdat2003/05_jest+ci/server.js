const express = require("express");
const mysql = require("mysql");
const bodyParser = require("body-parser");
const app = express();
const apiRoutes = express.Router();
app.use(bodyParser.json()); // for å tolke JSON
const PersonDao = require("./dao/persondao.js");

const pool = mysql.createPool({
  connectionLimit: 2,
  host: "mysql.stud.iie.ntnu.no",
  user: "nilstesd",
  password: "lqqWcMzq",
  database: "nilstesd",
  debug: false
});

let personDao = new PersonDao(pool);

app.get("/person", (req, res) => {
  console.log("/person: fikk request fra klient");
  personDao.getAll((status, data) => {
    res.status(status);
    res.json(data);
  });
});

app.get("/person/:personId", (req, res) => {
  console.log("/person/:personId: fikk request fra klient");
  personDao.getOne(req.params.personId, (status, data) => {
    res.status(status);
    res.json(data);
  });
});

app.post("/person", (req, res) => {
  console.log("Fikk POST-request fra klienten");
  personDao.createOne(req.body, (status, data) => {
    res.status(status);
    res.json(data);
  });
});

app.delete("/person/:personId", (req, res) => {
  console.log("/person/:personId: fikk request fra klient");
  personDao.deleteOne(req.params.personId, (status, data) => {
    res.status(status);
    res.json(data);
  });
});

app.put("/person/:personId", (req, res) => {
  console.log("Fikk POST-request fra klienten");
  personDao.updateOne({ ...req.body, id: personId }, (status, data) => {
    res.status(status);
    res.json(data);
  });
});

const server = app.listen(8080);
