package gov.usgs.aqcu.retrieval;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aquaticinformatics.aquarius.sdk.timeseries.servicemodels.Publish.UpchainProcessorListByTimeSeriesServiceRequest;
import gov.usgs.aqcu.exception.AquariusException;
import com.aquaticinformatics.aquarius.sdk.timeseries.servicemodels.Publish.ProcessorListServiceResponse;
import com.aquaticinformatics.aquarius.sdk.timeseries.servicemodels.Publish.Processor;

@Component
public class UpchainProcessorListService extends AquariusRetrievalService {
	private static final Logger LOG = LoggerFactory.getLogger(UpchainProcessorListService.class);

	public ProcessorListServiceResponse getRawResponse(String primaryTimeseriesIdentifier, Instant startDate, Instant endDate) throws AquariusException {
				UpchainProcessorListByTimeSeriesServiceRequest request = new UpchainProcessorListByTimeSeriesServiceRequest()
				.setTimeSeriesUniqueId(primaryTimeseriesIdentifier)
				.setQueryFrom(startDate)
				.setQueryTo(endDate);
		ProcessorListServiceResponse processorsResponse = executePublishApiRequest(request);
		return processorsResponse;
	}

	public List<String> getInputTimeSeriesUniqueIdList(List<Processor> processors) {
		Set<String> uniqueIds = new HashSet<>();

		for(Processor proc : processors) {
			uniqueIds.addAll(proc.getInputTimeSeriesUniqueIds());
		}

		return new ArrayList<>(uniqueIds);
	}

	public List<String> getRatingModelUniqueIdList(List<Processor> processors) {
		Set<String> uniqueIds = new HashSet<>();

		for(Processor proc : processors) {
			uniqueIds.add(proc.getInputRatingModelIdentifier());
		}

		return new ArrayList<>(uniqueIds);
	}
}
