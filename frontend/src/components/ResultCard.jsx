const BASE_URL = import.meta.env.VITE_API_BASE_URL;
import { useState } from "react";
import StatusBadge from "./StatusBadge";
import "../style/ResultCard.css";

export default function ResultCard({ result, mode }) {
  const [expanded, setExpanded] = useState(false);
  const [details, setDetails] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleToggle = async () => {
    const nextExpanded = !expanded;

    setExpanded(nextExpanded);

    if (details.length > 0 || !nextExpanded) {
      return;
    }

    try {
      setLoading(true);

      const token = localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/api/v1/recalls/detail?productName=${encodeURIComponent(result.productName)}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error("상세 조회 실패");
      }

      const data = await response.json();

      setDetails(data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="result-card">
      <div className="result-header" onClick={handleToggle}>
        <div className="header-left">
          <strong className="product-name">{result.productName}</strong>

          <span className="recall-count"> {result.recallCount}건</span>
        </div>

        <button className="detail-btn">{expanded ? "▲" : "▼"}</button>
      </div>

      {expanded && (
        <div className="detail-section">
          {loading && (
            <div className="loading-text">상세 정보를 불러오는 중...</div>
          )}

          {!loading && details.length === 0 && (
            <div className="empty-text">상세 정보가 없습니다.</div>
          )}

          {!loading &&
            details.map((detail, index) => (
              <div key={index} className="detail-card">
                <div className="detail-title">{index + 1}번 회수 정보</div>

                <div className="result-row">
                  <span>위험도</span>

                  <StatusBadge level={detail.dangerLevel} />
                </div>

                <div className="result-row">
                  <span>유효기간</span>

                  <strong>{detail.expirationDate || "-"}</strong>
                </div>

                <div className="result-row">
                  <span>LOT 번호</span>

                  <strong>{detail.lotNumber || "-"}</strong>
                </div>

                <div className="result-row">
                  <span>회수 사유</span>

                  <strong className="reason-text">
                    {detail.recallReason || "-"}
                  </strong>
                </div>

                <div className="result-row">
                  <span>회수일</span>

                  <strong>{detail.recallDate || "-"}</strong>
                </div>
              </div>
            ))}
        </div>
      )}
    </div>
  );
}
