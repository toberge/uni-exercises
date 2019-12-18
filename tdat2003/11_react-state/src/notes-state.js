// @flow

import * as React from 'react';
import { Component, sharedComponentData } from 'react-simplified';
import { HashRouter, Route, NavLink } from 'react-router-dom';
import ReactDOM from 'react-dom';

class HelloDumb extends Component {
  who = 'Trondheim';

  render() {
    return (
      <>
        <h2 style={{textAlign: 'center'}}>Hello {this.who}</h2>
      </>
    );
  }

  mounted(): void {
    // setInterval, setTimeout...
    console.log('1');
    setInterval(() => {
      console.log('3');
      this.who = 'Randelbrout';
    }, 10333);
    console.log('2');
    setInterval(() => {
      this.who = this.who === 'Trondheim'
        ? (this.who = 'miehdnorT')
        : 'Trondheim';
    }, 1000);
  }
}

class Hello extends Component {
  whos = [];

  // give li elements a key 4 later
  // gjør oppdatering mer effektivt (React)
  // why does he have Hello{' '} .......whay

  render() {
    return (
      <div style={{textAlign: 'right'}}>
        Hello
        <ul>
          {this.whos.map(e => <li key={e}>{e}</li>)}
        </ul>
      </div>
    );
  }

  mounted(): void {
    setTimeout(() => {
      this.whos.push('Trondheim');
    }, 1000);
    setTimeout(() => {
      this.whos.push('Bergen');
    }, 2000);
    setTimeout(() => {
      this.whos.push('Oslauv');
    }, 3000);
  }
}

// NB vanligvis inne i komponent men lurt å ha utenfor *hvis det skal deles*

// alternativt alt utenfor komponenten - komponenten må få vite om det...
// let shared = sharedComponentData({ whos: [] });

// i tilf. to komponenter må bruke samme data... da kan det være fint
let shared = sharedComponentData({ whos: [] });

setTimeout(() => {
  shared.whos.push('Trondheim');
}, 1000);
setTimeout(() => {
  shared.whos.push('Bergen');
}, 2000);
setTimeout(() => {
  shared.whos.push('Oslauv');
}, 3000);

class HelloSeparate extends Component {
  // give li elements a key 4 later
  // gjør oppdatering mer effektivt (React)
  // why does he have Hello{' '} .......whay

  render() {
    return (
      <>
        Hello shared
        <ul>
          {shared.whos.map(e => <li key={e}>{e}</li>)}
        </ul>
      </>
    );
  }

}


let root = document.getElementById('root');
if (root) ReactDOM.render(<><HelloDumb /><Hello/><HelloSeparate/></>, root);
