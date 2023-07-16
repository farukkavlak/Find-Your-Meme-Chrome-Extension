import React, { useState } from 'react';
import { UploadOutlined } from '@ant-design/icons';
import { Button, Col, Input, InputNumber, message, Row, Upload } from 'antd';
import axios from 'axios';
import Swal from "sweetalert2";
import { uploadImage } from "../functions/Functions";

const MemeUpload = () => {
  const [file, setFile] = useState(null);
  const [uploadProps, setUploadProps] = useState({
    beforeUpload: (file) => {
      const isImage =
        file.type === 'image/png' ||
        file.type === 'image/jpeg' ||
        file.type === 'image/jpg';
      if (!isImage) {
        Swal.fire({
          icon:  'error',
          title: 'Oops...',
          text:  `${file.name} is not an image file.`
        })
      }
      return isImage || Upload.LIST_IGNORE;
    },
    onChange: (info) => {
      if (info.file.status === 'done') {
        setFile(info.file.originFileObj);
      } else if (info.file.status === 'error') {
        Swal.fire({
          icon:  'error',
          title: 'Oops...',
          text:  `${info.file.name} upload failed.`
        })
      }
    },
  });

  const [tags, setTags] = useState([]);
  const [inputNum, setInputNum] = useState(1);
  const uploadMeme = async () => {
    await uploadImage(file, tags).then((res) => {
      if (res.status !== 200) {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: "Upload failed"
        })
        return
      }else {
        Swal.fire({
          icon:  'success',
          title: 'Success',
          text:  'Upload successfully'
        }).then(() => {
          setFile(null);
          setTags([]);
          setInputNum(1);
        });
      }
    });
  };
  return (
    <div>
      <Row gutter={[0, 10]} style={{ display: "flex", alignItems: "center" }}>
        <Col span={24}>
         <InputNumber min={1} max={5} defaultValue={1} onChange={(value) => setInputNum(value)} style={{ width: '100%', maxWidth: '100px' }}/>
        </Col>
        {
          [...Array(inputNum)].map((_, index) => {
            return (
              <Col span={24}>
                <Input
                  placeholder="Enter tags"
                  onChange={(e) => {
                    const newTags = [...tags];
                    newTags[index] = e.target.value.toLowerCase();
                    setTags(newTags);
                  }}
                  style={{ width: '100%', marginRight: 0, maxWidth: '100px' }}
                />
              </Col>
            );
          })
        }
        <Col span={24} justify="center">
          <Upload {...uploadProps} disabled={file !== null} onRemove={() => setFile(null)}>
            <Button icon={<UploadOutlined />}>Click to Upload</Button>
          </Upload>
        </Col>
        <Col span={24} justify="center">
        <Button
            onClick={uploadMeme}
            disabled={!file || tags.length === 0}
          >
            Upload
          </Button>
        </Col>
        
            
      </Row>
    </div>

  );
};

export default MemeUpload;
