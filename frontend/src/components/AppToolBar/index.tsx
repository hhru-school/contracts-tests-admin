import play from '../../img/play.svg';
import useSWR from 'swr';
import { fetchCustomData } from './api';
import React, { useState, KeyboardEvent, ChangeEvent, MouseEvent } from 'react';
import { ListGroup, ListGroupItem, Form, Input, Button } from 'reactstrap';
export const AppToolBar: React.FC = () => {
    const { data, error } = useSWR('/api/stands/', fetchCustomData);
    const [selectedItem, setSelectedItem] = useState('');
    const [showList, setShowList] = useState(false);
    const [hoveredItem, setHoveredItem] = useState<string>('');
    const [currentPositionInList, setCurrentPositionInList] = useState(0);
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

    const handleHover = (item: string) => {
        setHoveredItem(item);
    };
    const handleSearch = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedItem(e.target.value);
        setShowList(true);
    };
    const filteredData = data.stands
        .map((item: any) => item['name'])
        .filter((name: string) => name.toLowerCase().includes(selectedItem.toLowerCase()))
        .slice(0, 7);

    const selectElement = (item: string) => {
        setSelectedItem(item);
        setShowList(false);
        setCurrentPositionInList(0);
    };
    const handleSelect = (item: MouseEvent<HTMLElement>) => {
        selectElement(item.currentTarget.textContent as string);
    };
    const handleFocus = () => {
        setShowList(true);
    };
    const handleBlur = () => {
        selectElement(hoveredItem);
    };
    const handleKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
        console.log('handleKeyDown', event.key);
        if (event.key === 'Escape') {
            setShowList(false);
        } else if (event.key === 'ArrowDown') {
            if (currentPositionInList < filteredData.length) {
                setCurrentPositionInList(currentPositionInList + 1);
                const element = filteredData[currentPositionInList + 1].name;
                handleHover(element);
                //setSelectedItem(element);
            }
        } else if (event.key === 'ArrowUp') {
            if (currentPositionInList > 0) {
                setCurrentPositionInList(currentPositionInList - 1);
                const element = filteredData[currentPositionInList - 1].name;
                handleHover(element);
                //setSelectedItem(element);
            }
        }
    };
    return (
        <div className="row mb-5">
            <div className="col-12 d-inline d-flex align-items-center justify-content-center">
                <div className="d-block position-relative w-50">
                    <Form>
                        <Input
                            type="text"
                            placeholder="Search"
                            value={selectedItem}
                            onChange={handleSearch}
                            onFocus={handleFocus}
                            onBlur={handleBlur}
                            onKeyDown={handleKeyDown}
                        />
                    </Form>
                    {showList && (
                        <ListGroup
                            className="position-absolute w-100"
                            onSelect={handleSelect}
                            onKeyDown={handleKeyDown}
                        >
                            {filteredData.map((item: string) => (
                                <ListGroupItem
                                    key={item}
                                    onClick={() => selectElement(item)}
                                    onMouseEnter={() => handleHover(item)}
                                    onMouseLeave={() => handleHover('')}
                                    className={hoveredItem === item ? 'active' : ''}
                                >
                                    {item}
                                </ListGroupItem>
                            ))}
                        </ListGroup>
                    )}
                </div>
                <Button color="primary" className="ms-3">
                    <img src={play} /> Start
                </Button>
            </div>
        </div>
    );
};
