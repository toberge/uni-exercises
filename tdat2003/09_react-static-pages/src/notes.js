// @flow

import * as React from 'react';
import ReactDom from 'react-dom';
import { Component } from 'react-simplified';

//import * as Card from './Card';
const root = document.getElementById('root');

// se reactchildren i leksjon for... noe?
class Hello extends Component<{
  //who?: string
  children: React.Element<typeof Who>[]
}> {
  render() {
    /*return (
      <div>
        Hello {this.props.who}
      </div>
    );*/
    // dette gir potensielt undefined og vil da kræsje sida
    //return this.props.who;
    //return;
    // men det går fint å returnere null
    //return this.props.who? this.props.who : null;
    // kan returnere NULL før databasen har gitt tingene sine, sier han...
    return (
      <div>{this.props.children}</div>
    );
  }
}

class Who extends Component<{
  children: string | React.Element<'b'>
}> {
  render() {
    return (
      <div>
        {this.props.children}
      </div>
    );
  }
}

console.log(root);

let whos = ['Trondheim', 'Bergen'];
// maksgrense for antall med filter()

if (root) {
  // må returnere *noe* og kun én html-tag, kan bruke <>stuff</>
  // wee bit unnecessary to use children here, teacher says it should just be attribute apparently
  ReactDom.render(
    <>
      <Hello>
        <Who>Trondheim</Who>
        <Who><b>Hey</b></Who>
      </Hello>
    </>,
    root
  );
  /*ReactDom.render(
    <>
    <Hello who="Bjerken" />
      </>,
    root
  )*/
  //ReactDom.render(<Hello who="Trondheym" />, root);
  /*ReactDom.render(
    <div className="card">
      <div className="card-body">
        <h4 className="card-title">Hello there</h4>
        <p className="card-text">world's good place</p>
      </div>
    </div>,
    root
  );*/
  /*ReactDom.render(
    <div>Hei {
      whos
        .filter((e, i) => i < 1)
        .map(e => (
        <div>
          <b>{e}</b>
        </div>
      ))
    }
  </div>, root);*/
}