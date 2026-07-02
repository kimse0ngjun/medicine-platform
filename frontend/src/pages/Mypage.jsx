import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../style/MyPage.css";
import { getMyUser } from "../api/user";
import { getVerifications, deleteVerification } from "../api/verification";
import Pagination from "../components/Pagination";

const STATUS_KEY = {
  PENDING: "pending",
  PROCESSING: "processing",
  SUCCESS: "success",
  FAIL: "fail",
};

const STATUS_LABEL = {
  SUCCESS: "성공",
  PROCESSING: "처리 중",
  PENDING: "대기",
  FAIL: "실패",
};

const SEARCH_LABEL = {
  PRODUCT: "제품명 검색",
  LOT: "배치번호 검색",
  IMAGE: "이미지 조회",
};

const initial = (name) => (name ? name[0].toUpperCase() : "?");
const isWarning = (ms) => ms > 0 && ms < 5 * 60 * 1000;

const IconLogout = () => (
  <svg
    width="14"
    height="14"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
  >
    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
    <polyline points="16 17 21 12 16 7" />
    <line x1="21" y1="12" x2="9" y2="12" />
  </svg>
);

const IconClipboard = () => (
  <svg
    width="36"
    height="36"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="1.5"
  >
    <path d="M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2" />
    <rect x="9" y="3" width="6" height="4" rx="1" />
  </svg>
);

const IconLogo = ({ className }) => (
  <svg
    className={className}
    width="26"
    height="26"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="1.5"
  >
    <path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5" />
  </svg>
);

export default function MyPage() {
  const [user, setUser] = useState(null);
  const [remainTime, setRemainTime] = useState("");
  const [remainMs, setRemainMs] = useState(Infinity);
  const [verifications, setVerifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) return;

        const data = await getMyUser(token);
        setUser(data);
      } catch (err) {
        console.error(err);
      }
    };

    fetchUser();
  }, []);

  const handleDelete = async (id) => {
    try {
      await deleteVerification(id);

      setVerifications((prev) =>
        prev.filter((v) => Number(v.id) !== Number(id)),
      );
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      const stored = localStorage.getItem("user");
      if (!stored) {
        setRemainTime("");
        return;
      }

      const { expiresAt } = JSON.parse(stored);
      const diff = expiresAt - Date.now();

      if (diff <= 0) {
        localStorage.removeItem("user");
        window.location.reload();
        return;
      }

      setRemainMs(diff);
      const m = Math.floor(diff / 60000);
      const s = Math.floor((diff % 60000) / 1000);
      setRemainTime(
        `${String(m).padStart(2, "0")}:${String(s).padStart(2, "0")}`,
      );
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    const fetchVerifications = async () => {
      try {
        setLoading(true);

        const data = await getVerifications();

        setVerifications(data);
      } catch (e) {
        console.error(e);
      } finally {
        setLoading(false);
      }
    };

    fetchVerifications();
  }, []);

  const totalPages = Math.ceil(verifications.length / itemsPerPage);

  const currentItems = verifications.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage,
  );

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <>
      <header className="mypage-header">
        <Link to="/" className="mypage-header__brand">
          <IconLogo className="mypage-header__logo" />
          <span className="mypage-header__brand-text">MedicinePlatform</span>
        </Link>
        <div className="mypage-header__user">
          <div className="mypage-header__nickname">
            <strong>{user?.nickname}</strong>님
          </div>

          <button className="btn btn--danger" onClick={handleLogout}>
            <IconLogout />
            로그아웃
          </button>
        </div>
      </header>

      <main className="mypage-body">
        <div className="mypage-page-header">
          <div>
            <h1 className="mypage-page-title">마이페이지</h1>
            <p className="mypage-page-subtitle">
              계정 정보와 조회 이력을 확인하세요.
            </p>
          </div>
        </div>

        <div className="profile-card">
          <div className="profile-card__header">
            <span className="profile-card__title">내 정보</span>
          </div>

          <div className="mypage-profile-card__body">
            <div className="profile-field">
              <span className="profile-field__inline">
                닉네임: <strong>{user?.nickname ?? "—"}</strong>
              </span>
            </div>
            <div className="profile-field">
              <span className="profile-field__inline">
                이메일: <strong>{user?.email ?? "—"}</strong>
              </span>
            </div>
            <div className="profile-field">
              <span className="profile-field__inline">
                세션 잔여: <strong>{remainTime || "—"}</strong>
              </span>
            </div>
          </div>
        </div>

        <section className="verification-section">
          <div className="section-header">
            <h2 className="section-title">검증 이력</h2>
            {!loading && (
              <span className="section-count">{verifications.length}건</span>
            )}
          </div>

          {loading && (
            <div className="loading-state">
              <div className="loading-state__spinner" />
              불러오는 중...
            </div>
          )}

          {!loading && verifications.length === 0 && (
            <div className="empty-state">
              <IconClipboard />
              <p className="empty-state__title">조회 이력이 없습니다</p>
              <p className="empty-state__desc">
                리콜 조회를 하면 이곳에 기록됩니다.
              </p>
            </div>
          )}

          {!loading && verifications.length > 0 && (
            <>
              <div className="verification-list">
                {currentItems.map((v, i) => {
                  const key = STATUS_KEY[v.status] ?? "pending";

                  const isProduct = v.type === "PRODUCT";
                  const isLot = v.type === "LOT";
                  const isImage = v.type === "IMAGE";

                  return (
                    <div
                      key={v.id}
                      className={`verification-card verification-card--${key}`}
                      style={{ animationDelay: `${i * 40}ms` }}
                    >
                      <div className="verification-card__top">
                        <div className={`status-badge status-badge--${key}`}>
                          <span className="status-badge__dot" />
                          {STATUS_LABEL[key] ?? v.status}
                        </div>

                        <div className="verification-card__actions">
                          {v.createdAt && (
                            <span className="verification-card__date">
                              {new Date(v.createdAt).toLocaleString("ko-KR")}
                            </span>
                          )}

                          <button
                            className="delete-btn"
                            onClick={() => handleDelete(v.id)}
                          >
                            삭제
                          </button>
                        </div>
                      </div>

                      {isProduct && (
                        <div className="verification-card__body">
                          <div className="veri-row">
                            <span className="veri-row__key">조회방식</span>
                            <span className="veri-row__val">
                              {SEARCH_LABEL[v.type] ?? v.type}
                            </span>
                          </div>

                          <div className="veri-row">
                            <span className="veri-row__key">제품명</span>
                            <span className="veri-row__val">{v.inputText}</span>
                          </div>

                          <div className="veri-row">
                            <span className="veri-row__key">결과</span>
                            <span className="veri-row__val">
                              {v.result?.length > 120
                                ? `${v.result.slice(0, 120)}...`
                                : v.result}
                            </span>
                          </div>
                        </div>
                      )}

                      {isLot && (
                        <div className="verification-card__body">
                          <div className="veri-row">
                            <span className="veri-row__key">조회방식</span>
                            <span className="veri-row__val">
                              {SEARCH_LABEL[v.type] ?? v.type}
                            </span>
                          </div>

                          <div className="veri-row">
                            <span className="veri-row__key">LOT 번호</span>
                            <span className="veri-row__val">{v.lotNumber}</span>
                          </div>

                          <div className="veri-row">
                            <span className="veri-row__key">결과</span>
                            <span className="veri-row__val">{v.result}</span>
                          </div>
                        </div>
                      )}

                      {isImage && (
                        <div className="verification-card__body">
                          <div className="veri-row">
                            <span className="veri-row__key">조회방식</span>
                            <span className="veri-row__val">
                              {SEARCH_LABEL[v.type] ?? v.type}
                            </span>
                          </div>

                          <div className="veri-row">
                            <span className="veri-row__key">추출 LOT</span>
                            <span className="veri-row__val">{v.lotNumber}</span>
                          </div>

                          <div className="veri-row">
                            <span className="veri-row__key">결과</span>
                            <span className="veri-row__val">{v.result}</span>
                          </div>
                        </div>
                      )}
                    </div>
                  );
                })}
              </div>
              <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={setCurrentPage}
              />
            </>
          )}
        </section>
      </main>
    </>
  );
}
