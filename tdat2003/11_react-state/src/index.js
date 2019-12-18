// @flow
/* eslint eqeqeq: "off" */

import ReactDOM from 'react-dom';
import * as React from 'react';
import {Component} from 'react-simplified';
import {HashRouter, Route} from 'react-router-dom';
import {Alert, Card, NavBar, Button, Row, Column, Form, ListGroup, Link} from './widgets';

import {createHashHistory} from 'history';

const history = createHashHistory(); // Use history.push(...) to programmatically change path, for instance after successfully saving a student

class Student {
  id: number;
  static nextId = 1;

  firstName: string;
  lastName: string;
  email: string;

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

  constructor(code: string, title: string) {
    this.id = Course.nextId++;
    this.code = code;
    this.title = title;
  }

  toString = () => `${this.code} ${this.title}`;
}


const students = [
  new Student('Shinji', 'Ikari', 'get.in@robot.the'),
  new Student('Douglas', 'Adams', 'it.is.42@answer.se'),
  new Student('Ola', 'Jensen', 'ola.jensen@ntnu.no'),
  new Student('Kari', 'Larsen', 'kari.larsen@ntnu.no')
];

const courses = [
  new Course('TDAT2005', 'Algoritmer og datastrukturer'),
  new Course('TDAT2003', 'Systemutvikling 2 med web-applikasjoner')
];

const studentsInCourses = new Map<Student, Course[]>();
studentsInCourses.set(students[3], [...courses]);
studentsInCourses.set(students[2], [courses[0]]);

const studentsTakingCourses = new Map<Course, Student[]>();
courses.forEach(c => studentsTakingCourses.set(c, []));
courses.forEach(c => studentsInCourses.forEach((carr, student, m) => {
  if (carr.some(e => e === c)) {
    studentsTakingCourses.get(c).push(student);
  }
}));

class Menu extends Component {
  render() {
    return (
      <NavBar brand="NeoQS 2.0">
        <NavBar.Link to="/students">Students</NavBar.Link>
        <NavBar.Link to="/courses">Courses</NavBar.Link>
      </NavBar>
    );
  }
}

class Home extends Component {
  render() {
    return (
      <Card title="React example with component state">Client-server communication will be covered next week.</Card>
    );
  }
}

class StudentList extends Component {
  render() {
    return (
      <Card title="Students">
        <ListGroup>
          {students.map(student => (
            <ListGroup.Item>
              <Row key={student.id}>
                <Column width={'auto'}>
                  <Link exact to={'/students/' + student.id}>
                    {student.code} {student.title}
                  </Link>
                </Column>
                <Column right={true}>
                  <Link to={'/students/' + student.id + '/edit'}>
                    edit
                  </Link>
                </Column>
                <Column width={'auto'}>
                  <Link to={'/students/' + student.id + '/delete'}>
                    delete
                  </Link>
                </Column>
              </Row>
            </ListGroup.Item>
          ))}
          <ListGroup.Item>
            <Button.Success onClick={() => history.push('/students/new')}>
              Add student
            </Button.Success>
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}

class CourseList extends Component {
  render() {
    return (
      <Card title="Courses">
        <ListGroup>
          {courses.map(({id, toString}) => (
            <ListGroup.Item>
              <Row key={id}>
                <Column width={'auto'}>
                  <Link exact to={'/courses/' + id}>
                    {toString()}
                  </Link>
                </Column>
                <Column right={true}>
                  <Link to={'/courses/' + id + '/edit'}>
                    edit
                  </Link>
                </Column>
                <Column width={'auto'}>
                  <Link to={'/courses/' + id + '/delete'}>
                    delete
                  </Link>
                </Column>
              </Row>
            </ListGroup.Item>
          ))}
          <ListGroup.Item>
            <Button.Success onClick={() => history.push('/courses/new')}>
              Add course
            </Button.Success>
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}

class ThingList extends Component {
  subject: string;
  array: any[];


  constructor(subject: string, array: *[]) {
    super();
    this.subject = subject;
    this.array = array;
  }

  render() {
    return (
      <Card title={this.subject.charAt(0).toLocaleUpperCase() + this.subject.substring(1, this.subject.length)}>
        <ListGroup>
          {this.array.map(({id, toString}) => (
            <ListGroup.Item>
              <Row key={id}>
                <Column width={'auto'}>
                  <Link exact to={`/${this.subject}/${id}`}>
                    {toString()}
                  </Link>
                </Column>
                <Column right={true}>
                  <Link to={`/${this.subject}/${id}/edit`}>
                    edit
                  </Link>
                </Column>
                <Column width={'auto'}>
                  <Link to={`/${this.subject}/${id}/delete`}>
                    delete
                  </Link>
                </Column>
              </Row>
            </ListGroup.Item>
          ))}
          <ListGroup.Item>
            <Button.Success onClick={() => history.push(`/${this.subject}/new`)}>
              Add {this.subject.substring(0, this.subject.length - 1)}
            </Button.Success>
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}

class CourseListWrapper extends Component {
  thing = new ThingList('courses', courses);

  render() {
    return this.thing.render();
  }
}

class StudentListWrapper extends Component {
  thing = new ThingList('students', students);

  render() {
    return this.thing.render();
  }
}

class StudentDetails extends Component<{ match: { params: { id: number } } }> {
  render() {
    let student = students.find(student => student.id == this.props.match.params.id);
    if (!student) {
      if (this.props.match.params.id != 'new') // TODO fix this shit
        Alert.danger('Student not found: ' + this.props.match.params.id);
      else
        Alert.info('you gotta fix this');
      return null; // Return empty object (nothing to render)
    }
    let courses = studentsInCourses.get(student);
    return (
      <Card title="Details">
        <ListGroup>
          <ListGroup.Item>
            <Row>
              <Column width={2}>First name</Column>
              <Column>{student.firstName}</Column>
            </Row>
          </ListGroup.Item>
          <ListGroup.Item>
            <Row>
              <Column width={2}>Last name</Column>
              <Column>{student.lastName}</Column>
            </Row>
          </ListGroup.Item>
          <ListGroup.Item>
            <Row>
              <Column width={2}>Email</Column>
              <Column>{student.email}</Column>
            </Row>
          </ListGroup.Item>
          <ListGroup.Item>
            <h5>Courses</h5>
            {/*<Card title="Courses">*/}
            <ListGroup>
              {courses ? courses.map(e => (
                <ListGroup.Item key={e.id}>
                  {e.toString()}
                </ListGroup.Item>
              )) : 'none'}
            </ListGroup>
            {/*</Card>*/}
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}

class CourseDetails extends Component<{ match: { params: { id: number } } }> {
  render() {
    let course = courses.find(course => course.id == this.props.match.params.id);
    if (!course) {
      if (this.props.match.params.id != 'new') // TODO fix this shit
        Alert.danger('Course not found: ' + this.props.match.params.id);
      else
        Alert.info('you gotta fix this');
      return null; // Return empty object (nothing to render)
    }
    let students = studentsTakingCourses.get(course);
    return (
      <Card title="Details">
        <ListGroup>
          <ListGroup.Item key="code">
            <Row>
              <Column width={2}>Code</Column>
              <Column>{course.code}</Column>
            </Row>
          </ListGroup.Item>
          <ListGroup.Item key="title">
            <Row>
              <Column width={2}>Title</Column>
              <Column>{course.title}</Column>
            </Row>
          </ListGroup.Item>
          <ListGroup.Item key="students">
            <h5>Students</h5>
            {/*<Card title="Courses">*/}
            <ListGroup>
              {students ? students.map(e => (
                <ListGroup.Item key={e.id}>
                  {e.toString()}
                </ListGroup.Item>
              )) : 'none'}
            </ListGroup>
            {/*</Card>*/}
          </ListGroup.Item>
        </ListGroup>
      </Card>
    );
  }
}

class StudentEdit extends Component<{ match: { params: { id: number } } }> {
  firstName = ''; // Always initialize component member variables
  lastName = '';
  email = '';

  render() {
    return (
      <Card title="Edit">
        <Form>
          <ListGroup>
            <ListGroup.Item>
              <Row>
                <Column width={2}>First name</Column>
                <Column>
                  <Form.Input
                    value={this.firstName}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.firstName = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Last name</Column>
                <Column>
                  <Form.Input
                    value={this.lastName}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.lastName = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Email</Column>
                <Column>
                  <Form.Input
                    value={this.email}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.email = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
          </ListGroup>
          <Button.Danger onClick={this.save}>Save</Button.Danger>
        </Form>
      </Card>
    );
  }

  // Initialize component state (firstName, lastName, email) when the component has been inserted into the DOM (mounted)
  mounted() {
    let student = students.find(student => student.id == this.props.match.params.id);
    if (!student) {
      Alert.danger('Student not found: ' + this.props.match.params.id);
      return;
    }

    this.firstName = student.firstName;
    this.lastName = student.lastName;
    this.email = student.email;
  }

  save() {
    let student = students.find(student => student.id == this.props.match.params.id);
    if (!student) {
      Alert.danger('Student not found: ' + this.props.match.params.id);
      return;
    }

    student.firstName = this.firstName;
    student.lastName = this.lastName;
    student.email = this.email;

    // Go to StudentDetails after successful save
    history.push('/students/' + student.id);
  }
}

class CourseEdit extends Component<{ match: { params: { id: number } } }> {
  code = '';
  title = '';

  render() {
    return (
      <Card title="Edit">
        <Form>
          <ListGroup>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Code</Column>
                <Column>
                  <Form.Input
                    value={this.code}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.code = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Title</Column>
                <Column>
                  <Form.Input
                    value={this.title}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.title = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
          </ListGroup>
          <Button.Danger onClick={this.save}>Save</Button.Danger>
        </Form>
      </Card>
    );
  }

  mounted() {
    let course = courses.find(course => course.id == this.props.match.params.id);
    if (!course) {
      Alert.danger('Course not found: ' + this.props.match.params.id);
      history.push('/courses/');
      return;
    }

    this.code = course.code;
    this.title = course.title;
  }

  save() {
    let course = courses.find(course => course.id == this.props.match.params.id);
    if (!course) {
      Alert.danger('Course not found: ' + this.props.match.params.id);
      history.push('/courses/');
      return;
    }

    course.code = this.code;
    course.title = this.title;

    // Go to CourseDetails after successful edit
    history.push('/courses/' + course.id);
  }

}

class StudentCreate extends Component {
  firstName = '';
  lastName = '';
  email = '';

  render() {
    return (
      <Card title="New">
        <Form>
          <ListGroup>
            <ListGroup.Item>
              <Row>
                <Column width={2}>First name</Column>
                <Column>
                  <Form.Input
                    value={this.firstName}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.firstName = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Last name</Column>
                <Column>
                  <Form.Input
                    value={this.lastName}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.lastName = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Email</Column>
                <Column>
                  <Form.Input
                    value={this.email}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.email = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
          </ListGroup>
          <Button.Success onClick={this.create}>Create</Button.Success>
        </Form>
      </Card>
    );
  }

  create() {
    // create and add student
    const student = new Student(this.firstName, this.lastName, this.email);
    students.push(student);

    Alert.success('Added new student!');

    // Go to StudentDetails after successful creation
    history.push('/students/' + student.id);
  }

}

class CourseCreate extends Component {
  code = '';
  title = '';

  render() {
    return (
      <Card title="New">
        <Form>
          <ListGroup>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Code</Column>
                <Column>
                  <Form.Input
                    value={this.code}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.code = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
            <ListGroup.Item>
              <Row>
                <Column width={2}>Title</Column>
                <Column>
                  <Form.Input
                    value={this.title}
                    onChange={(event: SyntheticInputEvent<HTMLInputElement>) => (this.title = event.target.value)}
                  />
                </Column>
              </Row>
            </ListGroup.Item>
          </ListGroup>
          <Button.Success onClick={this.create}>Create</Button.Success>
        </Form>
      </Card>
    );
  }

  create() {
    // create and add course
    const course = new Course(this.code, this.title);
    courses.push(course);

    Alert.success('Added new course!');

    // Go to StudentDetails after successful creation
    history.push('/courses/' + course.id);
  }

}

class StudentDelete extends Component<{ match: { params: { id: number } } }> {

  render() {
    return (
      <Card title={`Delete student ${this.props.match.params.id}?`}>
        <Row>
          <Column width={2}>
            <Button.Danger onClick={this.delete}>Yep</Button.Danger>
            <Button.Light onClick={this.cancel}>Nope</Button.Light>
          </Column>
        </Row>
      </Card>
    );
  }

  delete() {
    // haven't gotten thrown out by check in mounted()
    students.splice(parseInt(this.props.match.params.id) - 1, 1);
    Alert.success('Student ' + this.props.match.params.id + ' deleted.');
    history.push('/students/');
  }

  cancel() {
    history.push('/students/' + this.props.match.params.id);
  }

  mounted() {
    // being extremely careful here
    let student = students.find(student => student.id == this.props.match.params.id);
    if (!student) {
      Alert.danger('Student not found: ' + this.props.match.params.id);
      history.push('/students/');
    }
  }
}

class CourseDelete extends Component<{ match: { params: { id: number } } }> {

  render() {
    return (
      <Card title={`Delete course ${this.props.match.params.id}?`}>
        <Row>
          <Column width={2}>
            <Button.Danger onClick={this.delete}>Yep</Button.Danger>
            <Button.Light onClick={this.cancel}>Nope</Button.Light>
          </Column>
        </Row>
      </Card>
    );
  }

  delete() {
    // haven't gotten thrown out by check in mounted()
    courses.splice(parseInt(this.props.match.params.id) - 1, 1);
    Alert.success('Course ' + this.props.match.params.id + ' deleted.');
    history.push('/courses/');
  }

  cancel() {
    history.push('/courses/' + this.props.match.params.id);
  }

  mounted() {
    // being extremely careful here
    let course = courses.find(course => course.id == this.props.match.params.id);
    if (!course) {
      Alert.danger('Course not found: ' + this.props.match.params.id);
      history.push('/courses/');
    }
  }
}

const root = document.getElementById('root');
if (root)
  ReactDOM.render(
    <HashRouter>
      <div>
        <Alert/>
        <Menu/>
        <Route exact path="/" component={Home}/>
        <Route path="/students" component={StudentListWrapper}/>
        <Route exact path="/students/new" component={StudentCreate}/>
        <Route exact path="/students/:id" component={StudentDetails}/>
        <Route exact path="/students/:id/edit" component={StudentEdit}/>
        <Route exact path="/students/:id/delete" component={StudentDelete}/>
        <Route path="/courses" component={CourseListWrapper}/>
        <Route exact path="/courses/new" component={CourseCreate}/>
        <Route exact path="/courses/:id" component={CourseDetails}/>
        <Route exact path="/courses/:id/edit" component={CourseEdit}/>
        <Route exact path="/courses/:id/delete" component={CourseDelete}/>
      </div>
    </HashRouter>,
    root
  );
