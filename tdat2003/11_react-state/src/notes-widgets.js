// @flow

import * as React from 'react';
import { Component, sharedComponentData } from 'react-simplified';
import { HashRouter, Route, NavLink } from 'react-router-dom';
import ReactDOM from 'react-dom';
import { Alert, Card, Column, Row } from './widgets';

// unngå default button for den har type="submit"
class ButtonPrimary extends Component <{ onClick: () => mixed, children?: React.Node, }> {
  render() {
    return <button className="btn btn-primary" type="button" onClick={this.props.onClick}>
      {this.props.children}
    </button>;
  }
}

class ButtonSecondary extends Component <{ onClick: () => mixed, children?: React.Node, }> {
  render() {
    return <button className="btn btn-secondary" type="button" onClick={this.props.onClick}>
      {this.props.children}
    </button>;
  }
}

class Button {
  static Primary = ButtonPrimary;
  static Secondary = ButtonSecondary;
}

class Hello extends Component {

  render() {
    return (
      <Card title="Very button yEs">
        <Row>
          <Column>
            <Button.Primary>Become Prime Minister</Button.Primary>
          </Column>
          <Column right>
            <Button.Secondary onClick={() => null}>Become Root</Button.Secondary>
          </Column>
        </Row>
      </Card>
    );
  }

  mounted(): unknown {
    Alert.danger('warning: danger');
    Alert.info('u is vewy dumb');
  }

}


let root = document.getElementById('root');
if (root) ReactDOM.render(
<>
  <Alert/>
  <Hello/>
</>, root);
