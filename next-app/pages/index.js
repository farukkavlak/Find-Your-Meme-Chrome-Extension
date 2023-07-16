import styles from '../styles/Home.module.css'
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { useState } from 'react';
import { Col, Row } from 'antd';
import MemeUpload from '../components/MemeUpload';
import MemeGrid from '../components/MemeGrid';

export default function Home() {
  const [choose, setChoose] = useState("find")

  const handleChooseChange = (event, newChoose) => {
    if(choose !== newChoose){
      setChoose(newChoose);
    }
  };

  return (
    <>
      <main className={styles.main}>
        <div>
          <Row gutter={[0, 16]}>
            <Col span={24} justify="center" align="middle">
              <ToggleButtonGroup
                value={choose}
                exclusive
                onChange={handleChooseChange}
                style={{ marginBottom: 6 }}
              >
                <ToggleButton value="find"  disabled={choose === "find"}>
                  FIND
                </ToggleButton>
                <ToggleButton value="upload" disabled={choose === "upload"}>
                  UPLOAD
                </ToggleButton>
              </ToggleButtonGroup>
            </Col>
            <Col span={24} justify="center" align="middle">
              {
                choose === "find" && (
                  <MemeGrid/>
                )
              }
              {
                choose === "upload" && (
                  <div>
                    <MemeUpload />
                  </div>
                )
              }
            </Col>

          </Row>


        </div>
      </main>
    </>
  )
}
