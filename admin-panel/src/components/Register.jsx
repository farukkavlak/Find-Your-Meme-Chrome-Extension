import { Col, Form, Input, Row } from 'antd';
import styles from './Login.module.css';
import { LockOutlined, LoadingOutlined } from '@ant-design/icons'
import { useState } from 'react';
import { register } from '../functions/AuthFunctions';
import Swal from 'sweetalert2'

const Register = ({closeCurrent}) => {
    const [user, setUser] = useState({});
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        setLoading(true);
        await register(user).then((res) => {
            let message = res.data.data.message;
            if(res.status === 200){
                Swal.fire({
                    title: 'Success',
                    text: message,
                    icon: 'success',
                    confirmButtonText: 'Ok'
                })
                setLoading(false);
                closeCurrent();
            }else {
                //Make alert for error
                Swal.fire({
                    title: 'Error',
                    text: message,
                    icon: 'error',
                    confirmButtonText: 'Ok'
                })
                setLoading(false);
            }
        });
    };
    return (
        <>
        <Row className={styles.loginContainer} justify='center' align='middle'>
                <Col className={styles.loginColumn} xxl={6} xl={6} lg={8} md={10} sm={14} xs={20} align='middle'>
                    <h1 className={styles.header}>Register</h1>
                    <Form
                        className={styles.formContainer}
                        name='registerForm'
                        onFinish={handleSubmit}
                    >
                        <Form.Item
                            className={styles.inputContainer}
                            name='Username'
                            rules={
                                [
                                    {
                                        required: true,
                                        message: "Username is required"
                                    },

                                ]
                            }>
                            <Input
                                className={styles.loginInput}
                                size='large'
                                type="text"
                                name="username"
                                id="username"
                                placeholder="Username"
                                onChange={(e) => {
                                    setUser({ ...user, username: e.target.value.trim() });
                                }}
                            />
                        </Form.Item>
                        <Form.Item
                            className={styles.inputContainer}
                            name='password'
                            rules={
                                [
                                    {
                                        required: true,
                                        message: "Password is required"
                                    },

                                ]
                            }>
                            <Input.Password
                                className={styles.loginInput}
                                size='large'
                                type="password"
                                name="password"
                                id="password"
                                placeholder="Password"
                                prefix={<LockOutlined />}
                                onChange={(e) => {
                                    setUser({ ...user, password: e.target.value.trim() });
                                }}
                            />
                        </Form.Item>
                        <button type="submit" className={styles.loginButton}>
                            {loading ? <LoadingOutlined /> : "Login"}
                        </button>
                    </Form>
                </Col>
            </Row>
        </>
    );
}   

export default Register;