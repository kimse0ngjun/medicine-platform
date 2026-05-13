import { useState } from "react";
import { checkRecall } from "../api/recall";
import { Link } from "react-router-dom";
import SearchBox from "../components/SearchBox";
import ResultCard from "../components/ResultCard";
import "../style/RecallPage.css";

import safeIcon from "../assets/icons/safe.svg";
import warningIcon from "../assets/icons/warning.png";
import recallIcon from "../assets/icons/recall.png";
import failIcon from "../assets/icons/fail.png";

const STATUS_LABEL = {
  SAFE: "안전",
  WARNING: "주의",
  RECALL: "리콜",
  FAIL: "조회 실패",
};

const STATUS_MESSAGE = {
  SAFE: "해당 의약품은 리콜 이력이 없습니다.",
  WARNING: "해당 의약품에 주의 사항이 있습니다.",
  RECALL: "해당 의약품은 리콜 대상입니다.",
  FAIL: "조회 중 오류가 발생했습니다.",
};

export default function RecallPage() {
  const [mode, setMode] = useState("LOT");
  const [lotNumber, setLotNumber] = useState("");
  const [imageFile, setImageFile] = useState(null);

  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const nickname = localStorage.getItem("nickname");

  const iconMap = {
    SAFE: safeIcon,
    WARNING: warningIcon,
    RECALL: recallIcon,
    FAIL: failIcon,
  };

  const handleLogout = () => {
    localStorage.removeItem("nickname");
    window.location.reload();
  };

  const handleSearch = async ({ mode, lotNumber, imageFile }) => {
    try {
      setLoading(true);
      setResult(null);

      let data;

      if (mode === "LOT") {
        if (!lotNumber.trim()) return;
        data = await checkRecall(lotNumber);
      }

      if (mode === "IMAGE") {
        if (!imageFile) return;

        const formData = new FormData();
        formData.append("image", imageFile);

        data = {
          status: "FAIL",
          message: "이미지 API 미연결 상태",
        };
      }

      setResult(data);
    } catch {
      setResult({
        status: "FAIL",
        message: "서버 오류가 발생했습니다.",
      });
    } finally {
      setLoading(false);
    }
  };

  const statusKey = result?.status?.toLowerCase() || "";

  return (
    <>
      <header className="recall-header">
        <Link to="/" className="recall-header__brand">
          <svg
            className="recall-header__brand-icon"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.5"
          >
            <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
          </svg>
          <span className="recall-header__brand-text">MedicinePlatform</span>
        </Link>

        <div className="recall-header__auth">
          {!nickname ? (
            <>
              <Link to="/auth/login">
                <button className="btn btn--ghost">로그인</button>
              </Link>
              <Link to="/auth/signup">
                <button className="btn btn--primary">회원가입</button>
              </Link>
            </>
          ) : (
            <>
              <span>
                <strong>{nickname}</strong>님
              </span>
              <Link to="/mypage">
                <button className="btn btn--ghost">마이페이지</button>
              </Link>
              <button className="btn btn--ghost" onClick={handleLogout}>
                로그아웃
              </button>
            </>
          )}
        </div>
      </header>

      <main className={`recall-page recall-page--${statusKey}`}>
        <div className="recall-hero">
          <span className="recall-hero__label">Drug Recall Search</span>
          <h1 className="recall-hero__title">의약품 리콜 조회</h1>
          <p className="recall-hero__subtitle">
            LOT 번호 또는 이미지로 리콜 여부를 확인합니다.
          </p>
        </div>

        <div className="recall-search-wrap">
          <SearchBox
            mode={mode}
            setMode={setMode}
            lotNumber={lotNumber}
            setLotNumber={setLotNumber}
            imageFile={imageFile}
            setImageFile={setImageFile}
            onSearch={handleSearch}
          />
        </div>

        {loading && (
          <div className="recall-loading">
            <div className="recall-loading__spinner" />
            <span>조회 중...</span>
          </div>
        )}

        {result && !loading && (
          <div className={`recall-status-row recall-status-row--${statusKey}`}>
            <img
              className="recall-status-icon"
              src={iconMap[result.status] ?? failIcon}
              alt={result.status}
            />

            <div className="recall-status-info">
              <div
                className={`recall-status-badge recall-status-badge--${statusKey}`}
              >
                <span className="recall-status-badge__dot" />
                {STATUS_LABEL[result.status] ?? result.status}
              </div>

              <p className="recall-status-message">
                {result.message ?? STATUS_MESSAGE[result.status]}
              </p>
            </div>
          </div>
        )}

        {result && !loading && (
          <div className="recall-result-wrap">
            <ResultCard result={result} />
          </div>
        )}
      </main>
    </>
  );
}
