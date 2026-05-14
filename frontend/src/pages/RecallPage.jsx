import { useEffect, useState } from "react";
import { checkRecall, checkRecallByImage, searchProduct } from "../api/recall";

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
  SAFE: "현재 등록된 회수 이력이 확인되지 않았습니다.",
  WARNING: "해당 의약품에 주의 사항이 있습니다.",
  RECALL: "과거 회수 이력이 존재합니다.",
  FAIL: "조회 중 오류가 발생했습니다.",
};

export default function RecallPage() {
  const [mode, setMode] = useState("PRODUCT");
  const [productName, setProductName] = useState("");
  const [lotNumber, setLotNumber] = useState("");
  const [imageFile, setImageFile] = useState(null);
  const [result, setResult] = useState(null);
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [remainTime, setRemainTime] = useState("");
  const [searched, setSearched] = useState(false);
  const storedUser = localStorage.getItem("user");

  let nickname = null;

  useEffect(() => {
    const interval = setInterval(() => {
      const storedUser = localStorage.getItem("user");

      if (!storedUser) {
        setRemainTime("");
        return;
      }

      const user = JSON.parse(storedUser);

      const diff = user.expiresAt - Date.now();

      if (diff <= 0) {
        localStorage.removeItem("user");

        setRemainTime("만료됨");

        clearInterval(interval);

        window.location.reload();

        return;
      }

      const minutes = Math.floor(diff / 1000 / 60);

      const seconds = Math.floor((diff / 1000) % 60);

      setRemainTime(
        `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`,
      );
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  if (storedUser) {
    const user = JSON.parse(storedUser);

    if (Date.now() < user.expiresAt) {
      nickname = user.nickname;
    } else {
      localStorage.removeItem("user");
    }
  }

  const iconMap = {
    SAFE: safeIcon,
    WARNING: warningIcon,
    RECALL: recallIcon,
    FAIL: failIcon,
  };

  const handleLogout = () => {
    localStorage.removeItem("user");

    window.location.reload();
  };

  const handleExtendSession = () => {
    const storedUser = localStorage.getItem("user");

    if (!storedUser) return;

    const user = JSON.parse(storedUser);

    user.expiresAt = Date.now() + 1000 * 60 * 60;

    localStorage.setItem("user", JSON.stringify(user));
  };

  const handleSearch = async () => {
    try {
      setSearched(true);

      setLoading(true);

      setResult(null);

      setResults([]);

      if (mode === "PRODUCT") {
        if (!productName.trim()) return;

        const data = await searchProduct(productName);

        setResults(data);

        return;
      }

      if (mode === "LOT") {
        if (!lotNumber.trim()) return;

        const data = await checkRecall(lotNumber);

        setResult(data);

        return;
      }

      if (mode === "IMAGE") {
        if (!imageFile) return;

        const formData = new FormData();

        formData.append("image", imageFile);

        const data = await checkRecallByImage(formData);

        setResult(data);
      }
    } catch (e) {
      console.error(e);

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
              <div>
                <strong>{nickname}</strong>님
              </div>

              <div className="recall-session">
                <span>{remainTime} 이용 가능</span>

                <button
                  className="btn btn--ghost"
                  onClick={handleExtendSession}
                >
                  시간 연장
                </button>
              </div>

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

          <h1 className="recall-hero__title">의약품 회수 이력 조회</h1>

          <p className="recall-hero__subtitle">
            제품명, LOT 번호 또는 이미지로 회수 이력을 확인합니다.
          </p>
        </div>

        <div className="recall-search-wrap">
          <SearchBox
            mode={mode}
            setMode={setMode}
            productName={productName}
            setProductName={setProductName}
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
          <>
            <div
              className={`recall-status-row recall-status-row--${statusKey}`}
            >
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

            <div className="recall-result-wrap">
              <ResultCard result={result} />
            </div>
          </>
        )}

        {!loading && results.length > 0 && (
          <div className="recall-result-list">
            {results.map((item, idx) => (
              <ResultCard key={idx} result={item} />
            ))}
          </div>
        )}

        {!loading && searched && mode === "PRODUCT" && results.length === 0 && (
          <div className="recall-empty">
            현재 등록된 회수 이력이 확인되지 않았습니다.
          </div>
        )}
      </main>
    </>
  );
}
