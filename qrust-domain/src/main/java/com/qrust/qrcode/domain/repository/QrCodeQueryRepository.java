package com.qrust.qrcode.domain.repository;

import com.qrust.qrcode.domain.entity.QQrCode;
import com.qrust.qrcode.domain.entity.QQrCodeImage;
import com.qrust.qrcode.dto.response.QrCodeListResponseDto;
import com.qrust.qrcode.dto.request.QrCodeSearchRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QrCodeQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QQrCode qrCode = QQrCode.qrCode;
    private final QQrCodeImage qrCodeImage = QQrCodeImage.qrCodeImage;

    public Page<QrCodeListResponseDto> searchQrCode(QrCodeSearchRequestDto dto, Pageable pageable) {
        BooleanBuilder condition = buildSearchCondition(dto);

        List<QrCodeListResponseDto> results = queryFactory
                .select(
                        Projections.constructor(
                                QrCodeListResponseDto.class,
                                qrCode.id,
                                qrCode.qrCodeImage.imageUrl,
                                qrCode.qrCodeData.title,
                                qrCode.createdAt,
                                qrCode.qrCodeData.url
                        ))
                .from(qrCode)
                .leftJoin(qrCode.qrCodeImage, qrCodeImage)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(qrCode.count())
                        .from(qrCode)
                        .where(condition)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanBuilder buildSearchCondition(QrCodeSearchRequestDto dto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.title() != null && !dto.title().isBlank()) {
            builder.and(qrCode.qrCodeData.title.containsIgnoreCase(dto.title()));
        }

        if (dto.start() != null && dto.end() != null) {
            builder.and(qrCode.createdAt.between(dto.start(), dto.end()));
        } else if (dto.start() != null) {
            builder.and(qrCode.createdAt.goe(dto.start()));
        } else if (dto.end() != null) {
            builder.and(qrCode.createdAt.loe(dto.end()));
        }

        if (dto.type() != null) {
            builder.and(qrCode.qrCodeType.eq(dto.type()));
        }

        if (dto.status() != null) {
            builder.and(qrCode.qrCodeStatus.eq(dto.status()));
        }

        return builder;
    }
}
