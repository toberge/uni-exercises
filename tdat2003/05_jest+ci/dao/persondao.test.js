const mysql = require("mysql");

const PersonDao = require("./persondao.js");
const runsqlfile = require("./runsqlfile.js");

// GitLab CI Pool
const pool = mysql.createPool({
  connectionLimit: 1,
  host: "mysql",
  user: "root",
  password: "secret",
  database: "supertestdb",
  debug: false,
  multipleStatements: true
});

let personDao = new PersonDao(pool);

beforeAll(done => {
  runsqlfile("dao/create_tables.sql", pool, () => {
    runsqlfile("dao/create_testdata.sql", pool, done);
  });
});

afterAll(() => {
  pool.end();
});

test("get one person from db", done => {
  function callback(status, data) {
    console.log(
      "Test callback: status=" + status + ", data=" + JSON.stringify(data)
    );
    expect(data.length).toBe(1);
    expect(data[0].navn).toBe("Hei Sveisen");
    done();
  }

  personDao.getOne(1, callback);
});

test("get unknown person from db", done => {
  function callback(status, data) {
    console.log(
      "Test callback: status=" + status + ", data=" + JSON.stringify(data)
    );
    expect(data.length).toBe(0);
    done();
  }

  personDao.getOne(0, callback);
});

test("add person to db", done => {
  function callback(status, data) {
    console.log(
      "Test callback: status=" + status + ", data=" + JSON.stringify(data)
    );
    expect(data.affectedRows).toBeGreaterThanOrEqual(1);
    done();
  }

  personDao.createOne(
    { navn: "Nils Nilsen", alder: 34, adresse: "Gata 3" },
    callback
  );
});

test("get all persons from db", done => {
  function callback(status, data) {
    console.log(
      "Test callback: status=" + status + ", data.length=" + data.length
    );
    expect(data.length).toBeGreaterThanOrEqual(2);
    done();
  }

  personDao.getAll(callback);
});

test('update person in db', done => {
  const verify = (status, data) => {
    console.log(
      `Test verify: status=${status}, data=${JSON.stringify(data)}`
    );
    // test them values
    const { navn, alder, adresse } = data[0];
    expect(navn).toBe("Grethe Sandstrak");
    expect(alder).toBe(50);
    expect(adresse).toBe("Vetikke");
    done();
  };

  const performUpdate = (status, data) => {
    console.log(
      `Test callback: status=${status}, data=${JSON.stringify(data)}`
    );
    expect(data.affectedRows).toBe(1);
    personDao.getOne(2, verify); // check that data actually changed
  };

  personDao.updateOne(
    { id: 2, navn: "Grethe Sandstrak", alder: 50, adresse: "Vetikke" },
    performUpdate
  );
});

test("delete person from db", done => {
  let length = -1;

  const verify = (status, data) => {
    console.log(
      `Test verify: status=${status}, data=${JSON.stringify(data)}`
    );
    expect(data.length).toBe(length - 1); // using known state
    done();
  };

  const performDeletion = (status, data) => {
    console.log(
      `Test callback: status=${status}, data=${JSON.stringify(data)}`
    );
    expect(data.affectedRows).toBe(1); // kanskje nok
    personDao.getAll(verify); // check if row count changed
  };

  const beforeDeletion = (status, data) => {
    console.log("Checking count");
    expect(data.length).toBeGreaterThan(0);
    length = data.length;
    personDao.deleteOne(1, performDeletion);
  };

  personDao.getAll(beforeDeletion);
});
