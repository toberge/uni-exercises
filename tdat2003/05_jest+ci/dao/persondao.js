const Dao = require("./dao.js");

module.exports = class PersonDao extends Dao {
  getAll(callback) {
    super.query("select navn, alder, adresse from person", [], callback);
  }

  getOne(id, callback) {
    super.query(
      "select navn, alder, adresse from person where id=?",
      [id],
      callback
    );
  }

  createOne(json, callback) {
    const val = [json.navn, json.adresse, json.alder];
    super.query(
      "insert into person (navn,adresse,alder) values (?,?,?)",
      val,
      callback
    );
  }

  // added by me:

  deleteOne(id, callback) {
    super.query(
        "DELETE FROM person WHERE id = ?",
        [id],
        callback
    );
  }

  updateOne(json, callback) {
    const val = [json.navn, json.adresse, json.alder, json.id];
    super.query(
      "UPDATE person SET navn = ?, adresse = ?, alder = ? WHERE id = ?",
      val,
      callback
    );
  }
};
