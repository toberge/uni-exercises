// @flow

import * as React from 'react';
import { Component } from 'react-simplified';
import { HashRouter, Route, NavLink } from 'react-router-dom';
import ReactDOM from 'react-dom';

class Home extends Component {
  render() {
    return (
      <div>
        Home
      </div>
    );
  }
}

class StudentList extends Component {
  render() {
    return (
      <div>Students:
      <div><NavLink activeStyle={{ color: 'darkblue' }} to="/students/1">1</NavLink></div>
        <div><NavLink activeStyle={{ color: 'darkblue' }} to="/students/2">2</NavLink></div>
      </div>
    );
    /*return (
      <div>
        Students:
        <a href="#/students/2">Click</a>
      </div>
    );*/
  }
}

// voldsomt med flow
class StudentDetails extends Component<{
  match: { params: { id: number } }
}> {
  render() {
    return (
      <div>
        Olá {this.props.match.params.id}
      </div>
    );
  }
}

const root = document.getElementById('root');
if (root) {
  ReactDOM.render(
    <HashRouter>
      <div>
        <Route exact path="/" component={Home} />
        <Route path="/students" component={StudentList} />
        <Route path="/students/:id" component={StudentDetails} />
      </div>
    </HashRouter>,
    root
  )
}
