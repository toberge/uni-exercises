// @flow
/* eslint eqeqeq: "off" */

import * as React from 'react';
import {Component} from 'react-simplified';
import {HashRouter, Route, NavLink} from 'react-router-dom';
import ReactDOM from 'react-dom';

class Student {
  id: number;
  static nextId = 1;

  firstName: string;
  lastName: string;
  email: string;
  courses: Course[] = [];

  constructor(firstName: string, lastName: string, email: string) {
    this.id = Student.nextId++;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  toString = () => `${this.firstName} ${this.lastName}`;
}

class Course {
  id: number;
  static nextId = 1;

  code: string;
  title: string;
  students: Student[];

  constructor(code: string, title: string, students: Student[]) {
    this.id = Course.nextId++;
    this.code = code;
    this.title = title;
    this.students = students;
  }

  toString = () => `${this.code} ${this.title}`;
}

const students = [
  new Student('Ola', 'Jensen', 'ola.jensen@ntnu.no'),
  new Student('Kari', 'Larsen', 'kari.larsen@ntnu.no'),
  new Student('Abraham', 'Gundersen', 'abraham.gundersen@ntnu.no'),
  new Student('Abraham', 'Lincoln', 'abraham.lincoln@ntnu.no'),
  new Student('Ola', 'Jensen 2', 'ola.jensen2@ntnu.no'),
  new Student('Ola', 'Jensen 3', 'ola.jensen3@ntnu.no')
];

const courses: Course[] = [
  new Course('TDAT2005', 'Algoritmer og datastrukturer', [
    students[0],
    students[1],
    students[2]
  ]),
  new Course('TDAT2003', 'Systemutvikling 2 med web-applikasjoner', [
    students[1],
    students[3]
  ])
];

// kinda crappy solution but it works
// assigning courses to students based on the student entries in the courses
courses.forEach(c => c.students.forEach(s => s.courses.push(c)));

class Menu extends Component {
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <NavLink className="navbar-brand" exact to="/">Navbar</NavLink>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <NavLink className="nav-link" exact to="/">Home</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/students">Students</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/courses">Courses</NavLink>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

class Home extends Component {
  render() {
    return (
      <Card title="Reactionary example of static pressure">
        <p>This is a pen.</p>
      </Card>
    );
  }
}

class CourseList extends Component {
  render() {
    return (
      <Card title="Courses">
        <ListGroup action={true}>
          {courses.map(course => (
            <NavLinkListItem to={'/courses/' + course.id}>
              {course.code} {course.title}
            </NavLinkListItem>
          ))}
        </ListGroup>
      </Card>
    );
  }
}

class CourseDetails extends Component<{ match: { params: { id: number } } }> {
  render() {
    const course = courses.find(course => course.id === parseInt(this.props.match.params.id));
    if (!course) {
      console.error('Course not found'); // aye aye
      return null;
    }
    const {code, title, students} = course;
    return (
      <Card title="Details">
        <ListGroup>
          <ListItem>Code: {code}</ListItem>
          <ListItem>Title: {title}</ListItem>
          {students.length > 0 ?
            <ListItem>
              <h3>Students</h3>
              <ListGroup>
                {students.map(student => (
                  <ListItem>
                    {student.firstName} {student.lastName}
                  </ListItem>
                ))}
              </ListGroup>
            </ListItem>
            : null}
        </ListGroup>
      </Card>
    );
  }
}

class StudentList extends Component {
  render() {
    return (
      <Card title="Students">
        <ListGroup action={true}>
          {students.map(student => (
            <NavLinkListItem to={'/students/' + student.id}>
              {student.firstName} {student.lastName}
            </NavLinkListItem>
          ))}
        </ListGroup>
      </Card>
    );
  }
}

class StudentDetails extends Component<{ match: { params: { id: number } } }> {
  render() {
    const student = students.find(student => student.id === parseInt(this.props.match.params.id));
    if (!student) {
      console.error('Student not found'); // Until we have a warning/error system (next week)
      return null; // Return empty object (nothing to render)
    }
    const {firstName, lastName, email, courses} = student;
    return (
      <Card title="Details">
        <ListGroup>
          <ListItem>First name: {firstName}</ListItem>
          <ListItem>Last name: {lastName}</ListItem>
          <ListItem>Email: {email}</ListItem>
          {courses.length > 0 ?
            <ListItem>
              <h3>Courses</h3>
              <ListGroup>
                {courses.map(({code, title}) => (
                  <ListItem>
                    {code} {title}
                  </ListItem>
                ))}
              </ListGroup>
            </ListItem>
            : null}
        </ListGroup>
      </Card>
    );
  }
}

// TODO position of props.children
class Card extends Component<{
  title: string,
  children?: React.ChildrenArray<React.Node>
}> {
  render() {
    return (
      <div className="card m-1">
        <div className="card-body">
          <h2 className="card-title">{this.props.title}</h2>
          {this.props.children ? this.props.children : null}
        </div>
      </div>
    );
  }
}

class NavLinkListItem extends Component<{
  exact?: boolean,
  to: string,
  children: React.Node
}> {
  render() {
    return (
      <NavLink className="list-group-item list-group-item-action" to={this.props.to}>
        {this.props.children}
      </NavLink>
    );
  }
}

class ListGroup extends Component<{
  action?: boolean,
  children: React.ChildrenArray<React.Node | ListItem | NavLinkListItem>
}> {
  render() {
    if (this.props.action) {
      return (
        <div className="list-group">
          {this.props.children}
        </div>
      );
    } else {
      return (
        <ul className="list-group">
          {this.props.children}
        </ul>
      );
    }
  }
}

class ListItem extends Component<{
  children: React.Node
}> {
  render() {
    return (
      <li className="list-group-item">
        {this.props.children}
      </li>
    );
  }
}

const root = document.getElementById('root');
if (root)
  ReactDOM.render(
    <HashRouter>
      <div>
        <Menu/>
        <Route exact path="/" component={Home}/>
        <Route path="/students" component={StudentList}/>
        <Route path="/students/:id" component={StudentDetails}/>
        <Route path="/courses" component={CourseList}/>
        <Route path="/courses/:id" component={CourseDetails}/>
      </div>
    </HashRouter>,
    root
  );
