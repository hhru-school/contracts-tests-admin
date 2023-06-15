import { Loader } from 'components/Loader';
import { ValidationDetail } from 'components/ValidationDetail';
import { getValidationInfo } from 'components/ValidationDetail/api';
import { ValidationResponse } from 'components/ValidationDetail/types/ValidationResponse';
import { useGlobalContext } from 'context/AppContext';
import { useParams } from 'react-router-dom';
import { Col, Row } from 'reactstrap';
import useSWR from 'swr';

export const ValidationDetailPage: React.FC = () => {
    const { standName } = useGlobalContext();
    const { validationId } = useParams();

    const { isLoading, isValidating, data } = useSWR<ValidationResponse>(
        standName ? `/api/stands/${standName}/validations/${validationId}` : null,
        getValidationInfo,
    );

    const pendingPage = !data || !standName || isLoading || isValidating;

    if (pendingPage) {
        return (
            <Row>
                <Col className="pt-5 d-flex justify-content-center">
                    {!standName && <h5>выберите стенд</h5>}
                    {(isLoading || isValidating) && <Loader />}
                </Col>
            </Row>
        );
    }

    if (!data) return null;

    return (
        <Row>
            <ValidationDetail standName={standName} relationsList={data.servicesRelations} />
        </Row>
    );
};
