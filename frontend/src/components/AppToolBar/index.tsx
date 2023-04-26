import play from '../../img/play.svg';
import useSWR from 'swr';
import { fetchCustomData } from './api';
import React, { useState } from 'react';
import { Form, FormControl, ListGroup } from 'react-bootstrap';

export const AppToolBar: React.FC = () => {
    const { data, error } = useSWR('/api/stands/', fetchCustomData);
    const [selectedItem, setSelectedItem] = useState('');
    const [showList, setShowList] = useState(true);
    const [hoveredItem, setHoveredItem] = useState(null);
    if (!data) {
        return (
            <div className="alert alert-primary" role="alert">
                Data is loading
            </div>
        );
    }
    if (error) {
        return (
            <div className="alert alert-danger" role="alert">
                Cant load data {error.message}
            </div>
        );
    }
    const handleHover = (item) => {
        setHoveredItem(item);
    };
    const handleSearch = (e) => {
        setSelectedItem(e.target.value);
        setShowList(true);
    };
    const filteredData = data.stands
        .filter((item) => item['name'].toLowerCase().includes(selectedItem.toLowerCase()))
        .slice(0, 7);

    const handleSelect = (item) => {
        setSelectedItem(item);
        setShowList(false);
    };
    return (
        <div className="row mb-5">
            <div className="col-12 d-inline d-flex align-items-center justify-content-center">
                <div className="d-block position-relative w-50">
                    <Form>
                        <FormControl
                            type="text"
                            placeholder="Search"
                            value={selectedItem}
                            onChange={handleSearch}
                        />
                    </Form>
                    {showList && (
                        <ListGroup className="position-absolute w-100">
                            {filteredData.map((item) => (
                                <ListGroup.Item
                                    key={item['name']}
                                    onClick={() => handleSelect(item['name'])}
                                    onMouseEnter={() => handleHover(item['name'])}
                                    onMouseLeave={() => handleHover(null)}
                                    className={hoveredItem === item['name'] ? 'active' : ''}
                                >
                                    {item['name']}
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    )}
                </div>
                <button type="button" className="btn btn-primary ms-3">
                    <img src={play} />
                </button>
            </div>
        </div>
    );
};
