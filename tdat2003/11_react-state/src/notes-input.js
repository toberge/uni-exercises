// @flow

import * as React from 'react';
import { Component, sharedComponentData } from 'react-simplified';
import { HashRouter, Route, NavLink } from 'react-router-dom';
import ReactDOM from 'react-dom';

type InputEvent = SyntheticInputEvent<HTMLInputElement>
class Hello extends Component {
  who = '';

  // created with ref attribute
  form = null;

  render() {
    return (
      <form id="form" ref={e => this.form = e}>
        Hello {this.who}
        <input
          type="text"
          value={this.who}
          onChange={(event: InputEvent) => {
            this.who = event.target.value;
          }}
          required
        />
        <button type="button" onClick={this.sayHello}>Say hi plz</button>
      </form>
    );
  }

  sayHello() {
    // le bad idea of id
    // let form = document.getElementById('form');
    // if (form) console.log(form.checkValidity());
    if (this.form && !this.form.checkValidity()) {
      alert('FUCK YOU');
      return;
    }
    alert('Hello tahr' + this.who);
  }

}


let root = document.getElementById('root');
if (root) ReactDOM.render(<Hello/>, root);
