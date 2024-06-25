package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.log.ActivityLogResponseDTO;
import shop.haui_megatech.domain.entity.ActivityLog;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.repository.ActivityLogRepository;
import shop.haui_megatech.service.ActivityLogService;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final MessageSourceUtil     messageSourceUtil;

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<ActivityLogResponseDTO>> getList(
            PaginationRequestDTO request
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields()).ascending()
                    : Sort.by(request.fields()).descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<ActivityLog> page = request.keyword() == null
                                 ? activityLogRepository.findAll(pageable)
                                 : activityLogRepository.search(request.keyword(), pageable);

        List<ActivityLog> activityLogs = page.getContent();

        return GlobalResponseDTO
                .<PaginatedMeta, List<ActivityLogResponseDTO>>builder()
                .meta(PaginatedMeta.builder()
                                   .pagination(Pagination
                                           .builder()
                                           .keyword(request.keyword())
                                           .pageIndex(request.index())
                                           .pageSize((short) page.getNumberOfElements())
                                           .totalItems(page.getTotalElements())
                                           .totalPages(page.getTotalPages())
                                           .build())
                                   .build())
                .data(activityLogs
                        .stream()
                        .map(item -> ActivityLogResponseDTO
                                .builder()
                                .id(item.getId())
                                .subject(item.getSubject().getUsername())
                                .operation(messageSourceUtil.getMessage(item.getOperation()))
                                .success(item.getSuccess())
                                .whenCreated(item.getWhenCreated())
                                .build()
                        )
                        .toList()
                )
                .build();
    }
}
