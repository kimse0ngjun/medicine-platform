import { useState } from "react";
import { checkRecall } from "../api/recall";
import SearchBox from "../components/SearchBox";
import ResultCard from "../components/ResultCard";
import "./RecallPage.css";

import safeIcon from "../assets/icons/safe.svg";
import warningIcon from "../assets/icons/warning.png";
import recallIcon from "../assets/icons/recall.png";
import failIcon from "../assets/icons/fail.png";

export default function RecallPage() {
  const [lotNumber, setLotNumber] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    try {
      setLoading(true);
      const data = await checkRecall(lotNumber);
      setResult(data);
    } catch (e) {
      setResult({
        status: "FAIL",
        message: "서버 오류",
      });
    } finally {
      setLoading(false);
    }
  };

  const iconMap = {
    SAFE: safeIcon,
    WARNING: warningIcon,
    RECALL: recallIcon,
    FAIL: failIcon,
  };

  return (
    <div className={`page ${result?.status?.toLowerCase() || ""}`}>
      <h2 className="title">의약품 리콜 조회</h2>

      {result && (
        <div className="status-row">
          <img
            className="status-icon"
            src={iconMap[result.status] || failIcon}
            alt="status"
          />
          <span className="status-text">{result.status}</span>
        </div>
      )}

      <SearchBox
        lotNumber={lotNumber}
        setLotNumber={setLotNumber}
        onSearch={handleSearch}
      />

      {loading && <p>조회 중...</p>}

      <ResultCard result={result} />
    </div>
  );
}
